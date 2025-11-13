package com.s_kugel.aldra.generator.bin;

import com.s_kugel.aldra.generator.configuration.MyBatisGeneratorConfiguration;
import com.s_kugel.aldra.generator.configuration.VerboseProgressCallback;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EntityGenerator implements ApplicationRunner, ExitCodeGenerator {

  private int exitCode;

  private final List<MyBatisGeneratorConfiguration> configurations;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    List<String> warnings = new ArrayList<>();

    var generatorConfig = new Configuration();
    var context = new Context(ModelType.FLAT);
    context.setId("aldra");
    context.setTargetRuntime("MyBatis3");
    context.addProperty("javaFileEncoding", "UTF-8");
    configurations.forEach(v -> v.configure(context));
    generatorConfig.addContext(context);

    var callback = new DefaultShellCallback(true);

    var generator = new MyBatisGenerator(generatorConfig, callback, warnings);
    generator.generate(new VerboseProgressCallback());

    warnings.forEach(log::warn);
    if (warnings.isEmpty()) {
      this.exitCode = 0;
    } else {
      this.exitCode = 9;
    }
  }

  @Override
  public int getExitCode() {
    return exitCode;
  }
}
