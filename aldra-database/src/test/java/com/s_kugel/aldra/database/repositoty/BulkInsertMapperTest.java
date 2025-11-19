package com.s_kugel.aldra.database.repositoty;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.s_kugel.aldra.database.TestConfiguration;
import com.s_kugel.aldra.database.entity.gen.BatchLog;
import com.s_kugel.aldra.database.repository.BatchLogMapper;
import com.s_kugel.aldra.enums.gen.BatchStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(
    classes = TestConfiguration.class,
    webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DBUnit(
    cacheConnection = false,
    leakHunter = true,
    caseSensitiveTableNames = true,
    caseInsensitiveStrategy = Orthography.LOWERCASE,
    allowEmptyFields = true)
@DBRider(dataSourceBeanName = "primaryDataSource")
@Transactional(transactionManager = "primaryTransactionManager")
@Slf4j
public class BulkInsertMapperTest {

  @Autowired //
  private BatchLogMapper batchLogMapper;

  private final TimeBasedEpochGenerator generator = Generators.timeBasedEpochGenerator();

  @Test
  @DisplayName("バッチログテーブルのバルクインサートが正しく行えること")
  @DataSet(value = "/datasets/BulkInsertMapperTest/batch_log/seed/batch_log.csv")
  void test_batch_log() {
    List<BatchLog> rows = new ArrayList<>();
    for (var i = 0; i < 100; i++) {
      var row =
          new BatchLog()
              .withId(generator.generate())
              .withBatchId("BAT%05d".formatted(i))
              .withBatchName("SampleBatch%05d".formatted(i))
              .withBatchNameJp("サンプルバッチ%05d".formatted(i))
              .withExitMessage("終了メッセージ%05d".formatted(i))
              .withExitDatetime(LocalDateTime.now())
              .withStatus(BatchStatus.values()[i % BatchStatus.values().length])
              .withCreatedAt(LocalDateTime.now())
              .withCreatedBy("UT")
              .withVersion(0);
      rows.add(row);
    }

    var result = batchLogMapper.bulkInsert(rows);

    assertAll(() -> assertEquals(100, result));
  }
}
