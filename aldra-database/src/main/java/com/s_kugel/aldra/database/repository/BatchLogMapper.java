package com.s_kugel.aldra.database.repository;

import com.s_kugel.aldra.database.entity.gen.BatchLog;
import com.s_kugel.aldra.database.repository.gen.BatchLogMapperDefault;
import com.s_kugel.aldra.enums.gen.BatchStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchLogMapper extends BatchLogMapperDefault, BulkInsertMapper {

  Integer countRunningTask(@Param("batchId") String batchId, @Param("status") BatchStatus status);

  Integer updateBatchEndLog(@Param("row") BatchLog row);
}
