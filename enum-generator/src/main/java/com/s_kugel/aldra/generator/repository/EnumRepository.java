package com.s_kugel.aldra.generator.repository;

import com.s_kugel.aldra.generator.entity.EnumEntity;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EnumRepository {

  @Select(
      """
      SELECT *
      FROM ${tableName}
      ORDER BY display_order
      """)
  List<EnumEntity> findAll(@Param("tableName") String tableName);

  @Select(
      """
      SELECT
        pd.description
      FROM pg_stat_user_tables AS psut
      JOIN pg_description AS pd
        ON psut.relid = pd.objoid
      WHERE psut.relname = #{tableName}
      """)
  Optional<String> findTableComment(@Param("tableName") String tableName);
}
