package com.s_kugel.aldra.generator.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataSourceProperties {

  String driverClassName;

  String url;

  String username;

  String password;
}
