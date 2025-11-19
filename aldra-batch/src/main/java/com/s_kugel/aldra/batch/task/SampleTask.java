package com.s_kugel.aldra.batch.task;

import com.s_kugel.aldra.batch.model.BatchContext;
import com.s_kugel.aldra.batch.model.BatchResult;
import com.s_kugel.aldra.database.repository.BatchLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SampleTask extends BusinessDateTask {

  public SampleTask(BatchLogMapper batchLogMapper) {
    super(batchLogMapper);
  }

  @Override
  public BatchResult execute(BatchContext context) {
    return BatchResult.success("sandbox");
  }
}
