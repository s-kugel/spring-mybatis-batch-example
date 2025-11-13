package com.s_kugel.aldra.generator.entity;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnumEntity implements Serializable {

  String code;

  String label;

  Integer displayOrder;

  String description;
}
