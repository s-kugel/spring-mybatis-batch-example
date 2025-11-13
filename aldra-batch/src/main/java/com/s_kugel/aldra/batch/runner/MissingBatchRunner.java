package com.s_kugel.aldra.batch.runner;

import com.s_kugel.aldra.batch.enums.ExitStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(AbstractBatchRunner.class)
@Slf4j
public class MissingBatchRunner implements ApplicationRunner, ExitCodeGenerator {

  @Override
  public int getExitCode() {
    return ExitStatus.FAILED.getExitCode();
  }

  @Override
  public void run(ApplicationArguments args) {
    log.info("起動対象のバッチが指定されていません");
  }
}
