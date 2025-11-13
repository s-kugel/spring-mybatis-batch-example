package com.s_kugel.aldra.batch.runner;

import com.s_kugel.aldra.batch.enums.ExitStatus;
import com.s_kugel.aldra.batch.task.BatchTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.util.StopWatch;

@Slf4j
public abstract class AbstractBatchRunner implements ApplicationRunner, ExitCodeGenerator {

  static final String PROPERTY_NAME = "app.batch.execute";

  private ExitStatus exitStatus;

  protected abstract String getBatchId();

  protected abstract String getBatchName();

  protected abstract String getBatchNameJP();

  protected abstract BatchTask getTask();

  @Override
  public int getExitCode() {
    return exitStatus.getExitCode();
  }

  @Override
  public void run(ApplicationArguments args) {
    var sw = new StopWatch(getBatchId());
    sw.start();
    log.info("[{}][{}]{}を開始します。", getBatchId(), getBatchName(), getBatchNameJP());

    var status = getTask().execute();
    this.exitStatus = status;

    sw.stop();
    log.info(
        "[{}][{}]{}が{}しました。処理時間={}ms",
        getBatchId(),
        getBatchName(),
        getBatchNameJP(),
        status.getDescription(),
        sw.getTotalTimeMillis());
  }
}
