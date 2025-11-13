package com.s_kugel.aldra.generator.configuration;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.ProgressCallback;

@Slf4j
public class VerboseProgressCallback implements ProgressCallback {

  @Override
  public void startTask(String taskName) {
    log.info(taskName);
  }
}
