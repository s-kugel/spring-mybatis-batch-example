package com.s_kugel.aldra.batch.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExitStatus {
  COMPLETED(0, "正常終了"),
  WARNING(3, "警告終了"),
  FAILED(9, "異常終了");

  private final int exitCode;

  private final String description;
}
