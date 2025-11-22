package com.s_kugel.aldra.batch.task;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import com.s_kugel.aldra.batch.model.BatchContext;
import com.s_kugel.aldra.batch.model.BatchResult;
import com.s_kugel.aldra.common.utils.DateTimeUtils;
import com.s_kugel.aldra.database.entity.gen.BatchLog;
import com.s_kugel.aldra.database.repository.BatchLogMapper;
import com.s_kugel.aldra.database.repository.BusinessCalendarMapper;
import com.s_kugel.aldra.enums.gen.BatchStatus;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
public abstract class BusinessDateTask implements BatchTask {

  private final BatchLogMapper batchLogMapper;

  private final BusinessCalendarMapper businessCalendarMapper;

  private final DateTimeUtils dateTimeUtils;

  private final TimeBasedEpochGenerator generator = Generators.timeBasedEpochGenerator();

  protected abstract BatchResult process(BatchContext batchContext);

  @Override
  public BatchContext initializeContext(String batchId, String batchName, String batchNameJP) {
    // 固定時刻設定のロード
    dateTimeUtils.loadFixedClock();
    // 基準日データの取得
    var businessCalendar = businessCalendarMapper.findCurrentBusinessDate();
    if (Objects.isNull(businessCalendar)) {
      throw new IllegalStateException("有効な基準日が設定されていません。");
    }
    return BatchContext.builder()
        .batchId(batchId)
        .batchName(batchName)
        .batchNameJP(batchNameJP)
        .basisDate(businessCalendar.getBasisDate())
        .previousBasisDate(businessCalendar.getPreviousBasisDate())
        .nextBasisDate(businessCalendar.getNextBasisDate())
        .startOfMonth(businessCalendar.getStartOfMonth())
        .endOfMonth(businessCalendar.getEndOfMonth())
        .businessDateFlag(businessCalendar.getBusinessDateFlag())
        .build();
  }

  @Override
  public boolean hasRunningSameTask(BatchContext context) {
    var count = batchLogMapper.countByBatchIdAndStatus(context.batchId(), BatchStatus.RUNNING);
    return count > 0;
  }

  @Transactional
  @Override
  public BatchLog startLog(BatchContext context) {
    var row =
        new BatchLog()
            .withId(generator.generate())
            .withBatchId(context.batchId())
            .withBatchName(context.batchName())
            .withBatchNameJp(context.batchNameJP())
            .withStatus(BatchStatus.RUNNING)
            .withBasisDate(context.basisDate())
            .withCreatedAt(DateTimeUtils.now())
            .withCreatedBy(context.batchId())
            .withVersion(0);
    batchLogMapper.insert(row);
    return row;
  }

  @Override
  public BatchResult execute(BatchContext context) {
    if (!context.businessDateFlag()) {
      return BatchResult.success("非営業日のためスキップします。");
    }
    try {
      return process(context);
    } catch (Exception e) {
      log.error("{}の処理に失敗しました。", this.getClass().getSimpleName(), e);
      return BatchResult.fail(e.getMessage());
    }
  }

  @Transactional
  @Override
  public void endLog(BatchContext context, BatchLog startLog, BatchResult batchResult) {
    var endLog =
        startLog
            .withStatus(batchResult.status())
            .withExitDatetime(DateTimeUtils.now())
            .withExitMessage(batchResult.message())
            .withUpdatedAt(DateTimeUtils.now())
            .withUpdatedBy(context.batchId())
            .withVersion(startLog.getVersion() + 1);
    batchLogMapper.updateBatchEndLog(endLog);
  }
}
