import org.flywaydb.gradle.task.AbstractFlywayTask

buildscript {
    dependencies {
        classpath(libs.flyway.database.postgresql)
    }
}

plugins {
    `java-library`
    alias(libs.plugins.flyway)
}

dependencies {
    runtimeOnly(libs.postgresql)
    runtimeOnly(libs.flyway.database.postgresql)
}

tasks.withType<AbstractFlywayTask> {
    notCompatibleWithConfigurationCache("because https://github.com/flyway/flyway/issues/3550")
}

flyway {
    url = "jdbc:postgresql://${System.getenv("ALDRA_DB_HOST") ?: "127.0.0.1"}:${System.getenv("ALDRA_DB_PORT") ?: "15432"}/${System.getenv("ALDRA_DB_NAME") ?: "aldra_db"}"
    user = System.getenv("ALDRA_DB_USER") ?: "aldra"
    password = System.getenv("ALDRA_DB_PASSWORD") ?: "aldra"
    schemas = arrayOf("public")
    locations = arrayOf(
        "filesystem:${projectDir.resolve("ddl")}",
        "filesystem:${projectDir.resolve("dml")}"
    )
    cleanDisabled = false
}
