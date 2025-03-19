plugins {
    id("java")
    id("org.openapi.generator") version "7.2.0"
}

group = "tomaszewski"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":application"))
    implementation("jakarta.servlet:jakarta.servlet-api:5.0.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.0")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
    testImplementation("org.junit.jupiter:junit-jupiter")
}

sourceSets {
    main {
        java.srcDir("$buildDir/generated/src/main/java")
    }
}

openApiGenerate {
    generatorName.set("spring")
    inputSpecRootDirectory.set("$rootDir/openApi")
    outputDir.set("$buildDir/generated/openApi")
    apiPackage.set("com.example.api")
    modelPackage.set("com.example.model")
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8",
            "useTags" to "true",
            "interfaceOnly" to "true",
            "useSpringBoot3" to "true"
        )
    )
}

openApiGenerate {
    generatorName.set("typescript-angular")
    inputSpecRootDirectory.set("$rootDir/openApi")
    outputDir.set("$rootDir/webapp/src/app/openApi")
    apiPackage.set("com.example.api")
    modelPackage.set("com.example.model")
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8",
            "useTags" to "true",
            "interfaceOnly" to "true",
            "ngVersion" to "12"
        )
    )
}


sourceSets {
    main {
        java.srcDir("$buildDir/generated/src/main/java")
    }
}

tasks.test {
    useJUnitPlatform()
}


tasks.named("build") {
    dependsOn("openApiGenerate")
}
