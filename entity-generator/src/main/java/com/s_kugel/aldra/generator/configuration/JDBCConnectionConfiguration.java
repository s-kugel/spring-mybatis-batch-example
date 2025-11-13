package com.s_kugel.aldra.generator.configuration;

import org.mybatis.generator.config.Context;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * see details below<br>
 * <a href="https://mybatis.org/generator/configreference/jdbcConnection.html">Document</a>
 */
@Configuration
public class JDBCConnectionConfiguration implements MyBatisGeneratorConfiguration {

  @Value("${spring.datasource.driver-class-name}")
  private String driverClassName;

  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  @Override
  public void configure(Context context) {
    var config = new org.mybatis.generator.config.JDBCConnectionConfiguration();
    config.setDriverClass(driverClassName);
    config.setConnectionURL(url);
    config.setUserId(username);
    config.setPassword(password);
    context.setJdbcConnectionConfiguration(config);
  }
}
