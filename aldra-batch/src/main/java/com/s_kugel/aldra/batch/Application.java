package com.s_kugel.aldra.batch;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(
    scanBasePackages = {
      "com.s_kugel.aldra.batch",
      "com.s_kugel.aldra.common",
      "com.s_kugel.aldra.database"
    })
@ConfigurationPropertiesScan(
    basePackages = {
      "com.s_kugel.aldra.batch",
      "com.s_kugel.aldra.common",
      "com.s_kugel.aldra.database"
    })
public class Application {

  public static void main(String[] args) {
    try (var context =
        new SpringApplicationBuilder(Application.class) //
            .bannerMode(Banner.Mode.OFF) //
            .build() //
            .run(args) //
    ) {
      System.exit(SpringApplication.exit(context));
    }
  }
}
