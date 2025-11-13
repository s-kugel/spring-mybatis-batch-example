package com.s_kugel.aldra.database.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataSourceProperties {

  String driverClassName;

  String poolName;

  int maximumPoolSize;

  int minimumIdle;

  int idleTimeout;

  int connectionTimeout;

  int leakDetectionThreshold;

  String url;

  String username;

  String password;
}
