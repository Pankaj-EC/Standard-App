import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.6"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
	kotlin("plugin.jpa") version "1.9.24"
}

group = "com.uaer"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	//Db
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	//Redis
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("redis.clients:jedis")
	implementation("com.auth0:java-jwt:3.18.2")
	//Password Crypto
	implementation("org.springframework.security:spring-security-core")
	implementation("org.springframework.security:spring-security-crypto")
	//Redis Session Auto
	//implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
	//implementation("org.springframework.session:spring-session-data-redis")
	//oracle
	implementation ("com.oracle.database.jdbc:ojdbc8:19.12.0.0")
	//PostgreDB
	implementation ("org.postgresql:postgresql:42.2.23")
	//my SQl
	implementation("mysql:mysql-connector-java:8.0.32")

	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql:42.2.23")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
