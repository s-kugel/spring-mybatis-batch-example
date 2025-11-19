plugins {
    `java-library`
    alias(libs.plugins.spotless)
}

dependencies {
    implementation(libs.lombok)
    annotationProcessor(libs.lombok)
    testImplementation(libs.lombok)
    testAnnotationProcessor(libs.lombok)
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
