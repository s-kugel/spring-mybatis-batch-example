package com.s_kugel.aldra.generator.configuration;

import org.mybatis.generator.config.Context;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * see details below<br>
 * <a href="https://mybatis.org/generator/configreference/javaClientGenerator.html">Document</a>
 */
@Configuration
public class JavaClientGeneratorConfiguration implements MyBatisGeneratorConfiguration {

  @Value("${outputDirectory}")
  private String outputDirectory;

  @Value("${targetMapperPackage}")
  private String targetMapperPackage;

  @Override
  public void configure(Context context) {
    var config = new org.mybatis.generator.config.JavaClientGeneratorConfiguration();
    config.setConfigurationType("ANNOTATEDMAPPER");
    config.setTargetPackage(targetMapperPackage);
    config.setTargetProject(outputDirectory);
    context.setJavaClientGeneratorConfiguration(config);
  }
}
