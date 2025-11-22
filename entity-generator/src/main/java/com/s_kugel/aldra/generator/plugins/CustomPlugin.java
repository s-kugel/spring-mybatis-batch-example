package com.s_kugel.aldra.generator.plugins;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

@Slf4j
public class CustomPlugin extends PluginAdapter {

  @Override
  public boolean validate(List<String> warnings) {
    return true;
  }

  @Override
  public boolean modelBaseRecordClassGenerated(
      TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    var superInterface = new FullyQualifiedJavaType("com.s_kugel.aldra.database.entity.Entity");
    topLevelClass.addImportedType(superInterface);
    topLevelClass.addSuperInterface(superInterface);

    // add method - Entity#getTableName()
    var getTableName = new Method("getTableName");
    getTableName.addAnnotation("@Override");
    getTableName.setVisibility(JavaVisibility.PUBLIC);
    getTableName.setReturnType(new FullyQualifiedJavaType("String"));
    getTableName.addBodyLine(
        "return \"%s\";".formatted(introspectedTable.getTableConfiguration().getTableName()));
    topLevelClass.addMethod(getTableName);

    // add method - Entity#getColumnNames()
    topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.List"));
    var getColumnNames = new Method("getColumnNames");
    getColumnNames.addAnnotation("@Override");
    getColumnNames.setVisibility(JavaVisibility.PUBLIC);
    getColumnNames.setReturnType(new FullyQualifiedJavaType("List<String>"));
    getColumnNames.addBodyLine(
        "return List.of(%s);"
            .formatted(
                introspectedTable.getAllColumns().stream()
                    .map(IntrospectedColumn::getActualColumnName)
                    .map("\"%s\""::formatted)
                    .collect(Collectors.joining(","))));
    topLevelClass.addMethod(getColumnNames);

    // add method - Entity#getJdbcTypes()
    topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.Map"));
    topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.LinkedHashMap"));
    topLevelClass.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.type.JdbcType"));
    var getJdbcTypes = new Method("getJdbcTypes");
    getJdbcTypes.addAnnotation("@Override");
    getJdbcTypes.setVisibility(JavaVisibility.PUBLIC);
    getJdbcTypes.setReturnType(new FullyQualifiedJavaType("Map<String, JdbcType>"));
    getJdbcTypes.addBodyLine(
        """
        Map<String, JdbcType> map = new LinkedHashMap<>();
        %s
        return map;
        """
            .formatted(
                introspectedTable.getAllColumns().stream()
                    .map(
                        v ->
                            "map.put(\"%s\", JdbcType.%s);"
                                .formatted(v.getJavaProperty(), v.getJdbcTypeName()))
                    .collect(Collectors.joining(System.lineSeparator()))));
    topLevelClass.addMethod(getJdbcTypes);

    return true;
  }

  @Override
  public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
    // add method - selectAll()
    interfaze.addImportedType(new FullyQualifiedJavaType("java.util.List"));
    var selectAll = new Method("selectAll");
    selectAll.setDefault(false);
    selectAll.setAbstract(true);
    selectAll.addAnnotation(
        "@Select(\"SELECT * FROM %s\")"
            .formatted(introspectedTable.getTableConfiguration().getTableName()));
    selectAll.setReturnType(
        new FullyQualifiedJavaType("List<%s>".formatted(introspectedTable.getBaseRecordType())));
    var insideResultAnnotation =
        introspectedTable.getAllColumns().stream()
            .map(
                v -> {
                  var columnName = v.getActualColumnName();
                  var javaProperty = v.getJavaProperty();
                  var jdbcType = v.getJdbcTypeName();
                  return "@Result(column=\"%s\", property=\"%s\", jdbcType=JdbcType.%s)"
                      .formatted(columnName, javaProperty, jdbcType);
                })
            .collect(Collectors.joining(","));
    selectAll.addAnnotation("@Results({%s})".formatted(insideResultAnnotation));
    interfaze.addMethod(selectAll);

    return true;
  }
}
