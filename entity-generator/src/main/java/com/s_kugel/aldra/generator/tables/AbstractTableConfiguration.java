package com.s_kugel.aldra.generator.tables;

import java.sql.JDBCType;
import java.util.Collections;
import java.util.List;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.IgnoredColumn;
import org.mybatis.generator.config.TableConfiguration;

/**
 * MyBatis Generatorにおける出力テーブルの設定。<br>
 * <a href="https://mybatis.org/generator/configreference/table.html">Document</a>
 */
public abstract class AbstractTableConfiguration implements ITableConfiguration {

  protected abstract String getTableName();

  protected abstract String getDomainObjectName();

  protected abstract String getMapperName();

  protected abstract String getSqlProviderName();

  protected boolean isSelectByExampleStatementEnabled() {
    return false;
  }

  protected boolean isCountByExampleStatementEnabled() {
    return false;
  }

  protected boolean isUpdateByExampleStatementEnabled() {
    return false;
  }

  protected boolean isDeleteByExampleStatementEnabled() {
    return false;
  }

  protected List<ColumnOverride> getColumnOverrides() {
    return Collections.emptyList();
  }

  protected List<IgnoredColumn> getIgnoredColumns() {
    return Collections.emptyList();
  }

  protected ColumnOverride generateColumnOverride(
      String columnName, String javaProperty, JDBCType jdbcType, Class<?> javaType) {
    var override = new ColumnOverride(columnName);
    override.setJavaProperty(javaProperty);
    override.setJdbcType(jdbcType.getName());
    override.setJavaType(javaType.getName());
    return override;
  }

  @Override
  public TableConfiguration getTableConfiguration(Context context) {
    var configuration = new TableConfiguration(context);
    configuration.setTableName(getTableName());
    configuration.setDomainObjectName(getDomainObjectName());
    configuration.setMapperName(getMapperName());
    configuration.setSqlProviderName(getSqlProviderName());
    configuration.setSelectByExampleStatementEnabled(isSelectByExampleStatementEnabled());
    configuration.setCountByExampleStatementEnabled(isCountByExampleStatementEnabled());
    configuration.setUpdateByExampleStatementEnabled(isUpdateByExampleStatementEnabled());
    configuration.setDeleteByExampleStatementEnabled(isDeleteByExampleStatementEnabled());
    getColumnOverrides().forEach(configuration::addColumnOverride);
    getIgnoredColumns().forEach(configuration::addIgnoredColumn);
    return configuration;
  }
}
