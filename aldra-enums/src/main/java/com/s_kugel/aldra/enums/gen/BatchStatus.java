package com.s_kugel.aldra.enums.gen;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * バッチステータス
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum BatchStatus {

  /**
   * 実行中
   */
  RUNNING("実行中", 1),

  /**
   * 警告終了
   */
  WARN("警告終了", 2),

  /**
   * 正常終了
   */
  SUCCESS("正常終了", 3),

  /**
   * 異常終了
   */
  FAIL("異常終了", 4);

  final String label;

  final Integer order;

  public static Optional<BatchStatus> fromCode(String code) {
    return Arrays.stream(values()).filter(v -> Objects.equals(v.name(), code)).findFirst();
  }

  public static Optional<BatchStatus> fromLabel(String label) {
    return Arrays.stream(values()).filter(v -> Objects.equals(v.label, label)).findFirst();
  }
}
