import com.github.gradle.node.pnpm.task.PnpmInstallTask
import com.github.gradle.node.pnpm.task.PnpmTask

plugins {
    java
    war
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.github.node-gradle.node") version "7.1.0"
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.spring") version "2.2.20"
    kotlin("kapt") version "2.2.20"
}

group = "dev.rogueai"
version = "0.0.1-SNAPSHOT"
description = "ciusky"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

node {
    download = true
}

sourceSets {
    main {
        resources {
            srcDir(layout.projectDirectory.dir("src/generated/resources"))
        }
    }
}

tasks.withType<JavaCompile> {
    val compilerArgs = options.compilerArgs
    compilerArgs.addAll(listOf("-Amapstruct.verbose=true", "-Amapstruct.defaultComponentModel=spring"))
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.18.0")
    // https://mvnrepository.com/artifact/commons-io/commons-io
    implementation("commons-io:commons-io:2.20.0")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("io.github.wimdeblauwe:htmx-spring-boot-thymeleaf:4.0.1")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.5")
    implementation("org.flywaydb:flyway-core")
    implementation("com.bucket4j:bucket4j-core:8.10.1")
    implementation("com.github.ben-manes.caffeine:caffeine:3.2.2")
    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
    implementation("org.mapstruct:mapstruct:1.6.3")
    kapt("org.mapstruct:mapstruct-processor:1.6.3")
    runtimeOnly("com.h2database:h2")
    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // auto reload spring boot classes and thymleaf templates
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val pnpmInstall = tasks.withType<PnpmInstallTask>()

val defaultTailwindArgs = listOf(
    "exec", "tailwindcss",
    "-i", "tailwind/main.css",
    "-o", "${layout.projectDirectory.asFile.path}/src/generated/resources/static/main.css",
//    "--minify"
)

val compileCss by tasks.registering(PnpmTask::class) {
    group = "ciusky"
    args = defaultTailwindArgs
    dependsOn(pnpmInstall)
}

val copyJsAssets by tasks.registering(Copy::class) {
    group = "ciusky"
    dependsOn(pnpmInstall)
    into(layout.projectDirectory.dir("src/generated/resources/static"))
    with(copySpec {
        from(
            files(
                "node_modules/htmx.org/dist/htmx.min.js",
                "node_modules/flowbite/dist/flowbite.min.js",
                "node_modules/flowbite/dist/flowbite.min.js.map",
                "node_modules/apexcharts/dist/apexcharts.min.js",
                "node_modules/apexcharts/dist/apexcharts.min.js.map",
            )
        )
    })
}

tasks.named<ProcessResources>("processResources") {
    dependsOn(compileCss, copyJsAssets)
}
