import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    id("org.jetbrains.kotlin.jvm") version "1.3.21"
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

extra["kotlin.version"] = plugins.getPlugin(KotlinPluginWrapper::class.java).kotlinPluginVersion

repositories {
    mavenLocal()
    mavenCentral()
}

application {
    mainClassName = "spring_functional.ApplicationKt"
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}

apply { plugin("com.github.johnrengelman.shadow") }

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:2.1.2.RELEASE")
    }
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compile("org.jetbrains.kotlin:kotlin-reflect")

    compile("org.springframework:spring-web")
    compile("org.springframework:spring-webflux")

    compile("org.springframework:spring-context") {
        exclude(module = "spring-aop")
    }
    compile("io.projectreactor.netty:reactor-netty")

    compile("ch.qos.logback:logback-classic")

    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    testCompile("io.projectreactor:reactor-test")
    testCompile("org.assertj:assertj-core")
    testCompile("org.junit.jupiter:junit-jupiter-api")
    testCompile("org.junit.jupiter:junit-jupiter-engine")
}
