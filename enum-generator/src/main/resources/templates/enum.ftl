package ${packageName};

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * ${domainName}
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ${className} {

  <#list definitions as definition>
  /**
   * ${definition.label}<#if definition.description?has_content><br>
   * ${definition.description}</#if>
   */
  ${definition.code}("${definition.label}", ${definition.order})<#if definition_has_next>,<#else>;</#if>

  </#list>
  final String label;

  final Integer order;

  public static Optional<${className}> fromCode(String code) {
    return Arrays.stream(values()).filter(v -> Objects.equals(v.name(), code)).findFirst();
  }

  public static Optional<${className}> fromLabel(String label) {
    return Arrays.stream(values()).filter(v -> Objects.equals(v.label, label)).findFirst();
  }
}
