package com.s_kugel.aldra.batch.task;

import com.s_kugel.aldra.batch.enums.ExitStatus;

public interface BatchTask {

  ExitStatus execute();
}
