import com.github.gradle.node.pnpm.task.PnpmInstallTask

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

group = "com.rogueai"
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

tasks.withType<JavaCompile> {
    val compilerArgs = options.compilerArgs
    compilerArgs.addAll(listOf("-Amapstruct.verbose=true", "-Amapstruct.defaultComponentModel=spring"))
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.github.wimdeblauwe:htmx-spring-boot-thymeleaf:4.0.1")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.5")
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

// Task to run Tailwind CSS build
val compileCss by tasks.registering(Exec::class) {
    group = "build"
    description = "Compile Tailwind CSS using npm script"
    workingDir = file(".")
    commandLine = listOf("pnpm", "run", "build:css")
    dependsOn(pnpmInstall)
}

val jsAssetsSpec: CopySpec = copySpec {
    from(
        files(
            "node_modules/htmx.org/dist/htmx.min.js",
            "node_modules/flowbite/dist/flowbite.min.js"
        )
    )
}

val copyJsAssets by tasks.registering(Copy::class) {
    dependsOn(pnpmInstall)
    into(layout.projectDirectory.dir("src/main/resources/static"))
    with(jsAssetsSpec)
}

tasks.named<ProcessResources>("processResources") {
    dependsOn(compileCss, copyJsAssets)
}
