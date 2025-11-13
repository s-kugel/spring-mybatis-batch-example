package com.s_kugel.aldra.generator.bin;

import com.s_kugel.aldra.generator.repository.EnumRepository;
import com.s_kugel.aldra.generator.tables.ITableConfiguration;
import freemarker.template.Configuration;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EnumGenerator implements ApplicationRunner, ExitCodeGenerator {

  private int exitCode;

  private final EnumRepository enumRepository;

  private final List<ITableConfiguration> tableConfigurations;

  @Value("${targetEnumPackage}")
  private String packageName;

  @Value("${outputDirectory}")
  private String outputDirectory;

  @Override
  public void run(ApplicationArguments args) {
    try {
      var targetDirectory = Paths.get(outputDirectory, packageName.split("\\."));
      if (Files.notExists(targetDirectory)) {
        Files.createDirectories(targetDirectory);
      }

      var cfg = new Configuration(Configuration.VERSION_2_3_34);
      cfg.setClassForTemplateLoading(this.getClass(), "/templates");
      cfg.setDefaultEncoding("UTF-8");
      cfg.setAutoFlush(true);
      var template = cfg.getTemplate("enum.ftl");

      for (var configuration : tableConfigurations) {
        log.info("Introspecting table {}", configuration.getTableName());

        var tableComment =
            enumRepository
                .findTableComment(configuration.getTableName())
                .orElseThrow(
                    () ->
                        new RuntimeException(
                            "not found %s".formatted(configuration.getTableName())));
        var rows = enumRepository.findAll(configuration.getTableName());
        var model = new HashMap<String, Object>();
        model.put("packageName", packageName);
        model.put("domainName", tableComment);
        model.put("className", configuration.getJavaClassName());
        var definitions =
            rows.stream()
                .map(
                    r -> {
                      var definition = new HashMap<String, Object>();
                      definition.put("code", r.getCode());
                      definition.put("label", r.getLabel());
                      definition.put("order", r.getDisplayOrder());
                      definition.put("description", r.getDescription());
                      return definition;
                    })
                .toList();
        model.put("definitions", definitions);

        log.info("Generating Enum class for table {}", configuration.getTableName());

        var outputFilePath =
            Files.createFile(targetDirectory.resolve(configuration.getJavaClassName() + ".java"));
        try (var writer = new FileWriter(outputFilePath.toFile(), StandardCharsets.UTF_8)) {
          template.process(model, writer);
        }

        log.info("Saving file {}", outputFilePath.getFileName());
      }

      this.exitCode = 0;
    } catch (Exception e) {
      log.error("Failed to Generate Enum class", e);
      this.exitCode = 9;
    }
  }

  @Override
  public int getExitCode() {
    return exitCode;
  }
}
