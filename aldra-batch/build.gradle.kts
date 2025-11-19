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

    implementation(libs.uuid)
    implementation(libs.mybatis.spring)

    implementation(libs.spring.boot.starter)
    implementation(project(":aldra-enums"))
    implementation(project(":aldra-database"))

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
