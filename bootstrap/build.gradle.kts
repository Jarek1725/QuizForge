plugins {
    id("java")
}

group = "tomaszewski"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":adapter"))
    implementation(project(":application"))
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    implementation("org.springframework.boot:spring-boot-starter:3.2.4")
    implementation("org.springframework.boot:spring-boot-starter-security:3.2.4")
    implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
    implementation ("jakarta.servlet:jakarta.servlet-api:6.0.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

}

tasks.test {
    useJUnitPlatform()
}