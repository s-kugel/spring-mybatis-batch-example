package com.s_kugel.aldra.generator.tables;

import org.springframework.context.annotation.Configuration;

@Configuration
public class BusinessCalendarTable extends AbstractTableConfiguration {

  @Override
  protected String getTableName() {
    return "business_calendar";
  }

  @Override
  protected String getDomainObjectName() {
    return "BusinessCalendar";
  }

  @Override
  protected String getMapperName() {
    return "BusinessCalendarMapperDefault";
  }

  @Override
  protected String getSqlProviderName() {
    return "BusinessCalendarSqlProviderDefault";
  }
}
