package com.s_kugel.aldra.generator.tables;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationDateTable extends AbstractTableConfiguration {

  @Override
  protected String getTableName() {
    return "application_date";
  }

  @Override
  protected String getDomainObjectName() {
    return "ApplicationDate";
  }

  @Override
  protected String getMapperName() {
    return "ApplicationDateMapperDefault";
  }

  @Override
  protected String getSqlProviderName() {
    return "ApplicationDateSqlProviderDefault";
  }
}
