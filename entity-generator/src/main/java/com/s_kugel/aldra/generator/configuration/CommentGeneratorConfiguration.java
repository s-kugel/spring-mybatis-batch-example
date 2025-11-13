package com.s_kugel.aldra.generator.configuration;

import org.mybatis.generator.config.Context;
import org.springframework.context.annotation.Configuration;

/**
 * see details below<br>
 * <a href="https://mybatis.org/generator/configreference/commentGenerator.html">Document</a>
 */
@Configuration
public class CommentGeneratorConfiguration implements MyBatisGeneratorConfiguration {

  @Override
  public void configure(Context context) {
    var config = new org.mybatis.generator.config.CommentGeneratorConfiguration();
    config.addProperty("suppressDate", "true");
    context.setCommentGeneratorConfiguration(config);
  }
}
