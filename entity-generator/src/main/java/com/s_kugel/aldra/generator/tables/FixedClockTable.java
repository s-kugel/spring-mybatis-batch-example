package com.s_kugel.aldra.generator.tables;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FixedClockTable extends AbstractTableConfiguration {

  @Override
  protected String getTableName() {
    return "fixed_clock";
  }

  @Override
  protected String getDomainObjectName() {
    return "FixedClock";
  }

  @Override
  protected String getMapperName() {
    return "FixedClockMapperDefault";
  }

  @Override
  protected String getSqlProviderName() {
    return "FixedClockSqlProviderDefault";
  }
}
