package com.s_kugel.aldra.database.repositoty;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.s_kugel.aldra.database.TestConfiguration;
import com.s_kugel.aldra.database.entity.gen.BatchLog;
import com.s_kugel.aldra.database.repository.BatchLogMapper;
import de.huxhorn.sulky.ulid.ULID;
import java.sql.JDBCType;
import java.time.LocalDateTime;
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

  private final ULID ulid = new ULID();

  @Test
  @DisplayName("バッチログテーブルのバルクインサートが正しく行えること")
  @DataSet(value = "/datasets/BulkInsertMapperTest/seed/batch_log.csv")
  void test_batch_log() {
    var result =
        batchLogMapper.bulkInsert(
            List.of(
                new BatchLog()
                    .withId(ulid.nextULID())
                    .withBatchId("BAT00001")
                    .withBatchName("SampleBatch - A")
                    .withBatchNameJp("サンプルバッチ - A")
                    .withExitDatetime(LocalDateTime.now())
                    .withExitMessage("message")
                    .withStatus(JDBCType.DATE)
                    .withCreatedAt(LocalDateTime.now())
                    .withCreatedBy("UT")
                    .withVersion(0),
                new BatchLog()
                    .withId(ulid.nextULID())
                    .withBatchId("BAT00002")
                    .withBatchName("SampleBatch - B")
                    .withBatchNameJp("サンプルバッチ - B")
                    .withExitDatetime(LocalDateTime.now())
                    .withExitMessage("hoge")
                    .withStatus(JDBCType.VARCHAR)
                    .withCreatedAt(LocalDateTime.now())
                    .withCreatedBy("UT")
                    .withVersion(0)));

    log.info("result: rows={}", result);
  }
}
