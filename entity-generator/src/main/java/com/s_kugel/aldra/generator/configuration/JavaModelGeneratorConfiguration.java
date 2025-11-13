package com.s_kugel.aldra.generator.configuration;

import org.mybatis.generator.config.Context;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * see details below<br>
 * <a href="https://mybatis.org/generator/configreference/javaModelGenerator.html">Document</a>
 */
@Configuration
public class JavaModelGeneratorConfiguration implements MyBatisGeneratorConfiguration {

  @Value("${outputDirectory}")
  private String outputDirectory;

  @Value("${targetEntityPackage}")
  private String targetEntityPackage;

  @Override
  public void configure(Context context) {
    var config = new org.mybatis.generator.config.JavaModelGeneratorConfiguration();
    config.setTargetPackage(targetEntityPackage);
    config.setTargetProject(outputDirectory);
    context.setJavaModelGeneratorConfiguration(config);
  }
}
