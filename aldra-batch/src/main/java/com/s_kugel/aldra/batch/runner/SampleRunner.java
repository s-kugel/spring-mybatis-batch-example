package com.s_kugel.aldra.batch.runner;

import static com.s_kugel.aldra.batch.runner.AbstractBatchRunner.PROPERTY_NAME;

import com.s_kugel.aldra.batch.task.BatchTask;
import com.s_kugel.aldra.batch.task.SampleTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = PROPERTY_NAME, havingValue = SampleRunner.BATCH_NAME)
@Slf4j
@RequiredArgsConstructor
public class SampleRunner extends AbstractBatchRunner {

  static final String BATCH_ID = "BAT999";

  static final String BATCH_NAME = "Sample";

  static final String BATCH_NAME_JP = "サンプル";

  private final SampleTask task;

  @Override
  protected String getBatchId() {
    return BATCH_ID;
  }

  @Override
  protected String getBatchName() {
    return BATCH_NAME;
  }

  @Override
  protected String getBatchNameJP() {
    return BATCH_NAME_JP;
  }

  @Override
  protected BatchTask getTask() {
    return task;
  }
}
