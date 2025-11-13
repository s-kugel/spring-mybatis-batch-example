package com.s_kugel.aldra.generator.tables;

import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchExitStatusTable implements ITableConfiguration {

  @Override
  public String getTableName() {
    return "batch_exit_status";
  }

  @Override
  public String getJavaClassName() {
    return "BatchExitStatus";
  }
}
