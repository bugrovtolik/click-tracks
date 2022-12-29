import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.spring") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
}

group = "com.abuhrov"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_13
java.targetCompatibility = JavaVersion.VERSION_13

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("org.springframework.boot:spring-boot-starter-mustache:2.5.6")
    implementation("org.springframework.boot:spring-boot-starter-webflux:2.6.1")
    implementation("org.springframework.boot:spring-boot-starter-rsocket:2.5.6")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "13"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
