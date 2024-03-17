plugins {
	java
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.example.social"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
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
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("com.mysql:mysql-connector-j:8.3.0")
	implementation("org.modelmapper:modelmapper:3.1.1")
	// JUnit 5
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.0")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.0")

	// Mockito
	testImplementation("org.mockito:mockito-core:3.12.4")
	testImplementation("org.mockito:mockito-junit-jupiter:3.12.4")

	implementation("org.springframework.boot:spring-boot-starter-security")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
