package com.s_kugel.aldra.database;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(
    basePackages = {
      "com.s_kugel.aldra.database",
    })
@ComponentScan(
    basePackages = {
      "com.s_kugel.aldra.database",
    })
public class TestConfiguration {}
