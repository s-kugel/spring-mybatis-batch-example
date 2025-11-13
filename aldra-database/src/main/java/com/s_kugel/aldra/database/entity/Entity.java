package com.s_kugel.aldra.database.entity;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.type.JdbcType;

public interface Entity {

  String getTableName();

  List<String> getColumnNames();

  Map<String, JdbcType> getJdbcTypes();
}
