package com.s_kugel.aldra.generator.configuration;

import org.mybatis.generator.config.Context;
import org.springframework.context.annotation.Configuration;

/**
 * see details below<br>
 * <a href="https://mybatis.org/generator/configreference/javaTypeResolver.html">Document</a>
 */
@Configuration
public class JavaTypeResolverConfiguration implements MyBatisGeneratorConfiguration {

  @Override
  public void configure(Context context) {
    var config = new org.mybatis.generator.config.JavaTypeResolverConfiguration();
    config.addProperty("forceBigDecimals", "true");
    config.addProperty("useJSR310Types", "true");
    context.setJavaTypeResolverConfiguration(config);
  }
}
