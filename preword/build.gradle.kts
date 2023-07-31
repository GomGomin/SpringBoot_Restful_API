plugins {
	java
	id("org.springframework.boot") version "2.7.14"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id("groovy")
}

group = "com.namhun.hello"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_11
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.mysql:mysql-connector-j")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// DB
	implementation ("org.springframework.boot:spring-boot-starter-jdbc")

	// groovy
	implementation("org.codehaus.groovy:groovy:3.0.8")

	// gson
	implementation ("com.google.code.gson:gson:2.9.0")

	// slf4j & logback
	implementation("org.slf4j:jcl-over-slf4j")
	implementation("ch.qos.logback:logback-classic")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
