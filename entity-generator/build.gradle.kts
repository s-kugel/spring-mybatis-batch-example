import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    application
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.spotless)
}

dependencies {
    implementation(libs.lombok)
    annotationProcessor(libs.lombok)
    testImplementation(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    implementation(libs.spring.boot.starter)
    implementation(libs.postgresql)
    implementation(libs.mybatis.generator.core)

    implementation(project(":aldra-enums"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

spotless {
    encoding("UTF-8")
    java {
        leadingTabsToSpaces()
        removeUnusedImports()
        trimTrailingWhitespace()
        forbidWildcardImports()
        formatAnnotations()
        endWithNewline()
        googleJavaFormat().skipJavadocFormatting()
    }
}

tasks.register<Delete>("cleanGeneratedCodes") {
    delete = setOf(
        "${project(":aldra-database").projectDir.resolve("src/main/java/com/s_kugel/aldra/database/entity/gen")}",
        "${project(":aldra-database").projectDir.resolve("src/main/java/com/s_kugel/aldra/database/repository/gen")}"
    )
}

tasks.named<BootRun>("bootRun") {
    dependsOn("cleanGeneratedCodes")
    args = listOf(
        "--outputDirectory=${project(":aldra-database").projectDir.resolve("src/main/java")}",
        "--targetEntityPackage=com.s_kugel.aldra.database.entity.gen",
        "--targetMapperPackage=com.s_kugel.aldra.database.repository.gen"
    )
}
