plugins {
    id("java")
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example.banking"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.13.0-M2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.4")
    implementation ("org.springframework.boot:spring-boot-starter-web:3.4.4")
    implementation("com.mysql:mysql-connector-j:9.2.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.5.1")
    implementation("jakarta.annotation:jakarta.annotation-api:3.0.0")
}

tasks.test {
    useJUnitPlatform()
}