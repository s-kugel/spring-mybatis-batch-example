package com.s_kugel.aldra.batch.task;

import com.s_kugel.aldra.batch.model.BatchContext;
import com.s_kugel.aldra.batch.model.BatchResult;
import com.s_kugel.aldra.database.entity.gen.BatchLog;

public interface BatchTask {

  BatchContext initializeContext(String batchId, String batchName, String batchNameJP);

  boolean hasRunningSameTask(BatchContext context);

  BatchLog startLog(BatchContext context);

  BatchResult execute(BatchContext context);

  void endLog(BatchContext context, BatchLog startLog, BatchResult batchResult);
}
