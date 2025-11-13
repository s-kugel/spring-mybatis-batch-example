package com.s_kugel.aldra.batch;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
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
