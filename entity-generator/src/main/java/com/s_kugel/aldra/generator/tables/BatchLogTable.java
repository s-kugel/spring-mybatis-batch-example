package com.s_kugel.aldra.generator.tables;

import com.s_kugel.aldra.enums.gen.BatchExitStatus;
import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.List;
import org.mybatis.generator.config.ColumnOverride;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchLogTable extends AbstractTableConfiguration {

  @Override
  protected String getTableName() {
    return "batch_log";
  }

  @Override
  protected String getDomainObjectName() {
    return "BatchLog";
  }

  @Override
  protected String getMapperName() {
    return "BatchLogMapperDefault";
  }

  @Override
  protected String getSqlProviderName() {
    return "BatchLogSqlProviderDefault";
  }

  @Override
  protected List<ColumnOverride> getColumnOverrides() {
    List<ColumnOverride> columnOverrides = new ArrayList<>();

    columnOverrides.add(
        generateColumnOverride("status", "status", JDBCType.VARCHAR, BatchExitStatus.class));

    return columnOverrides;
  }
}
