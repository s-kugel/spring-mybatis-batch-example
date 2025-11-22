import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    `java-library`
    alias(libs.plugins.spotless)
}

dependencies {
    implementation(libs.lombok)
    annotationProcessor(libs.lombok)
    testImplementation(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    implementation(libs.spring.boot.starter)
    implementation(libs.mybatis.spring)
    implementation(project(":aldra-database"))
    implementation(project(":aldra-enums"))

    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(libs.spring.boot.starter.test)
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

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events(
            "SKIPPED",
            "PASSED",
            "FAILED",
            "STANDARD_ERROR",
        )
        exceptionFormat = TestExceptionFormat.FULL
    }
}
