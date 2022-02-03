group = "ru.mospolytech"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    google()
}

plugins {
    java
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.spring") version "1.5.20"
    application
}

val exposedVersion = "0.32.1"
val kotlinxCoroutinesVersion = "1.5.1"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.security:spring-security-crypto")
    compileOnly("org.springframework.boot:spring-boot-configuration-processor")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-joda")

    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("joda-time:joda-time:2.10.10")

    implementation("org.postgresql:postgresql:42.2.22")
    implementation("org.liquibase:liquibase-core:3.8.0")
    implementation("org.jetbrains.exposed:exposed-spring-boot-starter:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jodatime:$exposedVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$kotlinxCoroutinesVersion")
}

application {
    mainClass.set("ru.mospolytech.tok_zhizni.TokZhizniApplication")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    val bootJarTask = this.bootJar
    withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
        onlyIf {
            !bootJarTask.get().state.skipped
        }
        imageName = "personal/${project.name}"
    }
}