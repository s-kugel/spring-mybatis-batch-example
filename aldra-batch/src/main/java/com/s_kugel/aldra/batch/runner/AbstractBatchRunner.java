package com.s_kugel.aldra.batch.runner;

import com.s_kugel.aldra.batch.model.BatchResult;
import com.s_kugel.aldra.batch.task.BatchTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.util.StopWatch;

@Slf4j
public abstract class AbstractBatchRunner implements ApplicationRunner, ExitCodeGenerator {

  static final String PROPERTY_NAME = "app.batch.execute";

  private BatchResult batchResult;

  protected abstract String getBatchId();

  protected abstract String getBatchName();

  protected abstract String getBatchNameJP();

  protected abstract BatchTask getTask();

  @Override
  public int getExitCode() {
    return switch (batchResult.status()) {
      case RUNNING -> 3;
      case WARN -> 5;
      case FAIL -> 9;
      case SUCCESS -> 0;
    };
  }

  @Override
  public void run(ApplicationArguments args) {
    var sw = new StopWatch(getBatchId());
    sw.start();
    log.info("[{}][{}]{}を開始します。", getBatchId(), getBatchName(), getBatchNameJP());

    var task = getTask();
    try {
      var context = task.initializeContext(getBatchId(), getBatchName(), getBatchNameJP());
      var hasRunningSameTask = task.hasRunningSameTask(context);
      if (hasRunningSameTask) {
        this.batchResult = BatchResult.warn("同一バッチが起動中のため処理を中断します。");
      } else {
        var startLog = task.startLog(context);
        var batchResult = task.execute(context);
        task.endLog(context, startLog, batchResult);
        this.batchResult = batchResult;
      }
    } catch (Exception e) {
      log.error("{}の処理で想定外のエラーが発生しました。", getBatchNameJP(), e);
      this.batchResult = BatchResult.fail(e.getMessage());
    }

    sw.stop();
    log.info(
        "[{}][{}]{}が{}しました。処理時間={}ms / 終了メッセージ={}",
        getBatchId(),
        getBatchName(),
        getBatchNameJP(),
        batchResult.status().getLabel(),
        sw.getTotalTimeMillis(),
        batchResult.message());
  }
}
