package com.s_kugel.aldra.database.repository;

import com.s_kugel.aldra.database.repository.gen.BatchLogMapperDefault;
import com.s_kugel.aldra.enums.gen.BatchStatus;
import java.util.UUID;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchLogMapper extends BatchLogMapperDefault, BulkInsertMapper {

  Integer updateBatchEndLog(
      @Param("id") UUID id,
      @Param("status") BatchStatus status,
      @Param("exitMessage") String exitMessage,
      @Param("updatedBy") String updatedBy);
}
