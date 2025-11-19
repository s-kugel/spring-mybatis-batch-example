import org.gradle.kotlin.dsl.named
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

    implementation(libs.freemarker)

    implementation(libs.spring.boot.starter)
    implementation(libs.mybatis.spring)
    implementation(libs.postgresql)
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
        "${project(":aldra-enums").projectDir.resolve("src/main/java/com/s_kugel/aldra/enums/gen")}"
    )
}

tasks.named<BootRun>("bootRun") {
    dependsOn("cleanGeneratedCodes")
    args = listOf(
        "--outputDirectory=${project(":aldra-enums").projectDir.resolve("src/main/java")}",
        "--targetEnumPackage=com.s_kugel.aldra.enums.gen",
    )
}
