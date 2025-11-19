package com.s_kugel.aldra.batch.model;

import com.s_kugel.aldra.enums.gen.BatchStatus;

public record BatchResult(BatchStatus status, String message) {

  public static BatchResult success() {
    return new BatchResult(BatchStatus.SUCCESS, null);
  }

  public static BatchResult success(String message) {
    return new BatchResult(BatchStatus.SUCCESS, message);
  }

  public static BatchResult warn() {
    return new BatchResult(BatchStatus.WARN, null);
  }

  public static BatchResult warn(String message) {
    return new BatchResult(BatchStatus.WARN, message);
  }

  public static BatchResult fail() {
    return new BatchResult(BatchStatus.FAIL, null);
  }

  public static BatchResult fail(String message) {
    return new BatchResult(BatchStatus.FAIL, message);
  }
}
