package com.s_kugel.aldra.batch.task;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import com.s_kugel.aldra.batch.model.BatchContext;
import com.s_kugel.aldra.batch.model.BatchResult;
import com.s_kugel.aldra.database.entity.gen.BatchLog;
import com.s_kugel.aldra.database.repository.BatchLogMapper;
import com.s_kugel.aldra.database.repository.BusinessCalendarMapper;
import com.s_kugel.aldra.enums.gen.BatchStatus;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
public abstract class BusinessDateTask implements BatchTask {

  private final BatchLogMapper batchLogMapper;

  private final BusinessCalendarMapper businessCalendarMapper;

  private final TimeBasedEpochGenerator generator = Generators.timeBasedEpochGenerator();

  protected abstract BatchResult process(BatchContext batchContext);

  @Override
  public boolean hasRunningSameTask(BatchContext context) {
    var count = batchLogMapper.countRunningTask(context.batchId(), BatchStatus.RUNNING);
    return count > 0;
  }

  @Transactional
  @Override
  public UUID startLog(BatchContext context) {
    var id = generator.generate();
    batchLogMapper.insert(
        new BatchLog()
            .withId(id)
            .withBatchId(context.batchId())
            .withBatchName(context.batchName())
            .withBatchNameJp(context.batchNameJP())
            .withStatus(BatchStatus.RUNNING)
            .withCreatedAt(LocalDateTime.now())
            .withCreatedBy(context.batchId())
            .withVersion(0));
    return id;
  }

  @Override
  public BatchResult execute(BatchContext context) {
    try {
      var businessCalendar = businessCalendarMapper.findCurrentBusinessDate();
      if (Objects.isNull(businessCalendar)) {
        return BatchResult.fail("有効な基準日が設定されていません");
      }
      if (!businessCalendar.getBusinessDateFlag()) {
        return BatchResult.success("非営業日のためスキップします");
      }

      return process(
          context.toBuilder()
              .basisDate(businessCalendar.getBasisDate())
              .previousBasisDate(businessCalendar.getPreviousBasisDate())
              .nextBasisDate(businessCalendar.getNextBasisDate())
              .startOfMonth(businessCalendar.getStartOfMonth())
              .endOfMonth(businessCalendar.getEndOfMonth())
              .build());
    } catch (Exception e) {
      log.error("{}の処理に失敗しました", this.getClass().getSimpleName(), e);
      return BatchResult.fail(e.getMessage());
    }
  }

  @Transactional
  @Override
  public void endLog(UUID batchLogId, BatchResult batchResult, BatchContext context) {
    batchLogMapper.updateBatchEndLog(
        batchLogId, batchResult.status(), batchResult.message(), context.batchId());
  }
}
