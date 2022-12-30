plugins {
    application
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.spring") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
}

group = "com.abuhrov"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("org.springframework.boot:spring-boot-starter-mustache:2.5.6")
    implementation("org.springframework.boot:spring-boot-starter-webflux:2.6.1")
    implementation("org.springframework.boot:spring-boot-starter-rsocket:2.5.6")
    implementation("com.google.cloud.tools:appengine-gradle-plugin:2.4.5")
}

application {
    mainClass.set("com.abuhrov.clicktracks.ClickTracksAppKt")
}

tasks {
    withType<Jar> {
        manifest {
            attributes["Main-Class"] = "com.abuhrov.clicktracks.ClickTracksAppKt"
        }
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from(sourceSets.main.get().output)
        dependsOn(configurations.runtimeClasspath)
        from({
            configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
        })
    }
    register("stage") {
        dependsOn("build")
    }
}
