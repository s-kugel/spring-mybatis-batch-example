package com.s_kugel.aldra.database.repository;

import com.s_kugel.aldra.database.entity.Entity;
import java.util.List;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BulkInsertMapper {

  @InsertProvider(type = BulkInsertSqlProvider.class, method = "bulkInsert")
  <T extends Entity> Integer bulkInsert(@Param("rows") List<T> rows);
}
