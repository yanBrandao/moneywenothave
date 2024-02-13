import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.2"
	id("io.gitlab.arturbosch.detekt") version "1.23.5"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	jacoco
}

group = "br.com.woodriver"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.postgresql:postgresql:42.7.1")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.h2database:h2:2.2.224")
	testImplementation ("io.kotest:kotest-runner-junit5:5.8.0")
	testImplementation ("io.kotest:kotest-assertions-core:5.8.0")


	implementation("io.kotest:kotest-framework-datatest:5.8.0")

	testImplementation("org.jeasy:easy-random-core:5.0.0")
	testImplementation("io.mockk:mockk:1.13.9")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
}
