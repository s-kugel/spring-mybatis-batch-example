package com.s_kugel.aldra.generator.configuration;

import com.s_kugel.aldra.generator.properties.DataSourceProperties;
import com.s_kugel.aldra.generator.properties.MyBatisProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@MapperScan(
    basePackages = {"com.s_kugel.aldra.generator.repository"},
    sqlSessionTemplateRef = "sqlSessionTemplate")
@Configuration
@RequiredArgsConstructor
public class DataSourceConfiguration {

  private static final ResourcePatternResolver RESOLVER = new PathMatchingResourcePatternResolver();

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSourceProperties dataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  public DataSource dataSource(
      @Qualifier("dataSourceProperties") final DataSourceProperties props) {
    HikariConfig config = new HikariConfig();
    config.setDriverClassName(props.getDriverClassName());
    config.setJdbcUrl(props.getUrl());
    config.setUsername(props.getUsername());
    config.setPassword(props.getPassword());
    return new HikariDataSource(config);
  }

  @Bean
  public PlatformTransactionManager transactionManager(
      @Qualifier("dataSource") final DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  @ConfigurationProperties(prefix = "mybatis")
  public MyBatisProperties myBatisProperties() {
    return new MyBatisProperties();
  }

  @Bean
  @SneakyThrows
  public SqlSessionFactory sqlSessionFactory(
      @Qualifier("dataSource") final DataSource dataSource,
      @Qualifier("myBatisProperties") final MyBatisProperties props) {
    var bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);
    bean.setMapperLocations(RESOLVER.getResources(props.getMapperLocations()));
    bean.setConfiguration(props.getConfiguration());
    return bean.getObject();
  }

  @Bean
  @Primary
  public SqlSessionTemplate sqlSessionTemplate(
      @Qualifier("sqlSessionFactory") final SqlSessionFactory factory) {
    return new SqlSessionTemplate(factory);
  }
}
