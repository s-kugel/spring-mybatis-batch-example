package com.s_kugel.aldra.generator.tables;

import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchStatusTable implements ITableConfiguration {

  @Override
  public String getTableName() {
    return "batch_status";
  }

  @Override
  public String getJavaClassName() {
    return "BatchStatus";
  }
}
