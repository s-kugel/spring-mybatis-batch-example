package com.s_kugel.aldra.database.configuration;

import com.s_kugel.aldra.database.properties.DataSourceProperties;
import com.s_kugel.aldra.database.properties.MyBatisProperties;
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
    basePackages = {"com.s_kugel.aldra.database.repository"},
    sqlSessionTemplateRef = "primarySqlSessionTemplate")
@Configuration
@RequiredArgsConstructor
public class DataSourceConfiguration {

  private static final ResourcePatternResolver RESOLVER = new PathMatchingResourcePatternResolver();

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.primary")
  public DataSourceProperties primaryDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @Primary
  public DataSource primaryDataSource(
      @Qualifier("primaryDataSourceProperties") final DataSourceProperties props) {
    HikariConfig config = new HikariConfig();
    config.setDriverClassName(props.getDriverClassName());
    config.setJdbcUrl(props.getUrl());
    config.setUsername(props.getUsername());
    config.setPassword(props.getPassword());
    config.setPoolName(props.getPoolName());
    config.setMaximumPoolSize(props.getMaximumPoolSize());
    config.setMinimumIdle(props.getMinimumIdle());
    config.setIdleTimeout(props.getIdleTimeout());
    config.setConnectionTimeout(props.getConnectionTimeout());
    config.setLeakDetectionThreshold(props.getLeakDetectionThreshold());
    return new HikariDataSource(config);
  }

  @Bean
  @Primary
  public PlatformTransactionManager primaryTransactionManager(
      @Qualifier("primaryDataSource") final DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "mybatis")
  public MyBatisProperties primaryMyBatisProperties() {
    return new MyBatisProperties();
  }

  @Bean
  @Primary
  @SneakyThrows
  public SqlSessionFactory primarySqlSessionFactory(
      @Qualifier("primaryDataSource") final DataSource dataSource,
      @Qualifier("primaryMyBatisProperties") final MyBatisProperties props) {
    var bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);
    bean.setMapperLocations(RESOLVER.getResources(props.getMapperLocations()));
    bean.setConfiguration(props.getConfiguration());
    return bean.getObject();
  }

  @Bean
  @Primary
  public SqlSessionTemplate primarySqlSessionTemplate(
      @Qualifier("primarySqlSessionFactory") final SqlSessionFactory factory) {
    return new SqlSessionTemplate(factory);
  }
}
