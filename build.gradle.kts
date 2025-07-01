plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.server"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")

    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.8.0")

    implementation("org.springframework.data:spring-data-r2dbc:3.2.0")
    implementation("org.springframework.data:spring-data-commons:3.2.0")
    implementation("org.springframework.data:spring-data-relational:3.2.0")
    implementation("org.springframework.data:spring-data-keyvalue:3.2.0")
    implementation("org.apache.lucene:lucene-core:9.11.0")
    implementation("org.apache.lucene:lucene-queryparser:9.11.0")
    implementation("org.apache.lucene:lucene-analysis-common:9.11.0")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.projectlombok:lombok")
    testImplementation("org.springframework.security:spring-security-test")

    // MacOS용 Netty DNS (M1/M2 등 arm64 전용)
    runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.112.Final:osx-aarch_64")

    // PostgreSQL 드라이버 + R2DBC 드라이버
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.postgresql:r2dbc-postgresql")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")

    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.6.0")

    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
