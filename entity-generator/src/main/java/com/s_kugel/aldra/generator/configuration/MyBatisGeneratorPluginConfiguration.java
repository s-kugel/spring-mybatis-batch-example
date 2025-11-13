package com.s_kugel.aldra.generator.configuration;

import com.s_kugel.aldra.generator.plugins.CustomPlugin;
import java.util.Set;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.plugins.EqualsHashCodePlugin;
import org.mybatis.generator.plugins.FluentBuilderMethodsPlugin;
import org.mybatis.generator.plugins.MapperAnnotationPlugin;
import org.mybatis.generator.plugins.SerializablePlugin;
import org.mybatis.generator.plugins.ToStringPlugin;
import org.springframework.context.annotation.Configuration;

/**
 * see details below<br>
 * <a href="https://mybatis.org/generator/configreference/plugin.html">Document</a>
 */
@Configuration
public class MyBatisGeneratorPluginConfiguration implements MyBatisGeneratorConfiguration {

  private static final Set<Class<? extends PluginAdapter>> USE_PLUGINS =
      Set.of(
          SerializablePlugin.class,
          MapperAnnotationPlugin.class,
          ToStringPlugin.class,
          EqualsHashCodePlugin.class,
          FluentBuilderMethodsPlugin.class,
          CustomPlugin.class);

  @Override
  public void configure(Context context) {
    USE_PLUGINS.stream()
        .map(
            v -> {
              var config = new PluginConfiguration();
              config.setConfigurationType(v.getName());
              return config;
            })
        .forEach(context::addPluginConfiguration);
  }
}
