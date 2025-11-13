package com.s_kugel.aldra.generator.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.ibatis.session.Configuration;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyBatisProperties {

  String mapperLocations;

  Configuration configuration;
}
