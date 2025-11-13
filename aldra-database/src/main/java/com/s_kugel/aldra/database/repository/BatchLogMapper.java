package com.s_kugel.aldra.database.repository;

import com.s_kugel.aldra.database.repository.gen.BatchLogMapperDefault;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchLogMapper extends BatchLogMapperDefault, BulkInsertMapper {}
