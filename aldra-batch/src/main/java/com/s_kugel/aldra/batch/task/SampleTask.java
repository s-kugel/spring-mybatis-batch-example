package com.s_kugel.aldra.batch.task;

import com.s_kugel.aldra.batch.enums.ExitStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SampleTask implements BatchTask {

  @Override
  public ExitStatus execute() {
    log.info("foo - bar");
    return ExitStatus.FAILED;
  }
}
