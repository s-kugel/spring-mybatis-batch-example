package com.s_kugel.aldra.batch.task;

import com.s_kugel.aldra.batch.model.BatchContext;
import com.s_kugel.aldra.batch.model.BatchResult;
import java.util.UUID;

public interface BatchTask {

  UUID startLog(BatchContext context);

  BatchResult execute(BatchContext context);

  void endLog(UUID batchLogId, BatchResult batchResult, BatchContext context);
}
