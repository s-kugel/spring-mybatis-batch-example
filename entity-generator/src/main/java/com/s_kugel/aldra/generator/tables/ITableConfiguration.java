package com.s_kugel.aldra.generator.tables;

import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.TableConfiguration;

public interface ITableConfiguration {

  TableConfiguration getTableConfiguration(Context context);
}
