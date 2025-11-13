package com.s_kugel.aldra.database.repository;

import com.s_kugel.aldra.database.entity.Entity;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.type.JdbcType;

public class BulkInsertSqlProvider {

  public <T extends Entity> String bulkInsert(@Param("rows") List<T> rows) {
    if (Objects.isNull(rows) || rows.isEmpty()) {
      throw new IllegalArgumentException("rows must not be null or empty");
    }

    var first = rows.getFirst();
    var sb = new StringBuilder();
    var tableName = first.getTableName();
    var columns = String.join(",", first.getColumnNames());
    sb.append(
        "INSERT INTO %s (%s) VALUES %s".formatted(tableName, columns, System.lineSeparator()));

    Map<String, JdbcType> jdbcTypes = first.getJdbcTypes();
    var rowCounter = new AtomicInteger(-1);
    rows.forEach(
        v -> {
          if (rowCounter.incrementAndGet() != 0) {
            sb.append(",");
          }
          var values =
              jdbcTypes.entrySet().stream()
                  .map(
                      e ->
                          "#{rows[%d].%s,jdbcType=%s}"
                              .formatted(rowCounter.get(), e.getKey(), e.getValue().name()))
                  .collect(Collectors.joining(", "));
          sb.append("(%s)%n".formatted(values));
        });

    return sb.toString();
  }
}
