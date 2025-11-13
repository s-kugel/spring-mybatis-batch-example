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
        googleJavaFormat()
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
        // "--driverClassName=org.postgresql.Driver",
        // "--url=jdbc:postgresql://${System.getenv("ALDRA_DB_HOST") ?: "127.0.0.1"}:${System.getenv("ALDRA_DB_PORT") ?: "15432"}/${System.getenv("ALDRA_DB_NAME") ?://  "aldra_db"}",
        // "--username=${System.getenv("ALDRA_DB_USER") ?: "aldra"}",
        // "--password=${System.getenv("ALDRA_DB_PASSWORD") ?: "aldra"}",
        "--outputDirectory=${project(":aldra-database").projectDir.resolve("src/main/java")}",
        "--targetEntityPackage=com.s_kugel.aldra.database.entity.gen",
        "--targetMapperPackage=com.s_kugel.aldra.database.repository.gen"
    )
}
