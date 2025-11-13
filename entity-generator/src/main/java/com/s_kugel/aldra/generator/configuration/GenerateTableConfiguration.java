package com.s_kugel.aldra.generator.configuration;

import com.s_kugel.aldra.generator.tables.AbstractTableConfiguration;
import com.s_kugel.aldra.generator.tables.ITableConfiguration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mybatis.generator.config.Context;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Generatorにおける出力対象のテーブル設定。<br>
 * 具体的な設定内容は{@link AbstractTableConfiguration}を参照。
 */
@Configuration
@RequiredArgsConstructor
public class GenerateTableConfiguration implements MyBatisGeneratorConfiguration {

  private final List<ITableConfiguration> tableConfigurations;

  @Override
  public void configure(Context context) {
    tableConfigurations.stream()
        .map(v -> v.getTableConfiguration(context))
        .forEach(context::addTableConfiguration);
  }
}
