import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.diffplug.spotless") version "6.23.3"
    id("jacoco")
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.21"
}

group = "com.clp-runner"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

sourceSets {
    main {
        java {
            srcDirs(arrayOf("${projectDir}/src/main/java", "${projectDir}/build/generated"))
        }
    }
}

spotless {
    val excludeFiles = arrayOf(".idea/**/*.*", ".vscode/**/*.*")

    java {
        targetExclude("build/**/*.java")
        removeUnusedImports()
        replaceRegex("Remove wildcard imports", "import\\s+[^\\*\\s]+\\*;(\\r\\n|\\r|\\n)", "$1")
        googleJavaFormat()
        importOrder(
            "java",
            "jakarta",
            "javax",
            "lombok",
            "org.springframework",
            "",
            "org.junit",
            "com.runner-talk",
            "\\#",
            "\\#org.junit",
            "\\#com.runner-talk",
        )
        indentWithTabs(2)
        indentWithSpaces(4)
        trimTrailingWhitespace()
        endWithNewline()
    }
    format("yaml") {
        target("**/*.yaml", "**/*.yml")
        targetExclude(*excludeFiles)
        prettier().configFile(".prettierrc")
    }
    format("xml") {
        target("**/*.xml")
        targetExclude(*excludeFiles)
        prettier().config(mapOf("parser" to "html", "printWidth" to 160)).configFile(".prettierrc")
    }
    format("md") {
        target("**/*.md")
        targetExclude(*excludeFiles)
        prettier().config(mapOf("printWidth" to 160)).configFile(".prettierrc")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.kafka:spring-kafka")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.test {
    useJUnitPlatform()
    if (JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_13)) {
        jvmArgs("-XX:+AllowRedefinitionToAddDeleteMethods")
    }
    systemProperties["spring.profiles.active"] = "test"
}

tasks.jacocoTestReport {
    reports {
        html.required.set(true)
        xml.required.set(true)
        csv.required.set(false)
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            element = "CLASS"
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.85".toBigDecimal()
            }
            excludes = listOf(
                "com.clprunner.runnertalkchat.RunnerTalkChatApplication",
            )
        }
    }
}

val testCoverage by tasks.registering {
    group = "verification"
    description = "Runs the unit tests with coverage"
    dependsOn("test",
        "jacocoTestReport",
        "jacocoTestCoverageVerification")
    tasks["jacocoTestReport"].mustRunAfter(tasks["test"])
    tasks["jacocoTestCoverageVerification"].mustRunAfter(tasks["jacocoTestReport"])
}

val testAll by tasks.registering {
    group = "verification"
    description = "Runs all tests"
    dependsOn("spotlessCheck",
        "test",
        "jacocoTestReport",
        "jacocoTestCoverageVerification")
    tasks["test"].mustRunAfter(tasks["spotlessCheck"])
    tasks["jacocoTestReport"].mustRunAfter(tasks["test"])
    tasks["jacocoTestCoverageVerification"].mustRunAfter(tasks["jacocoTestReport"])
}

val jar: Jar by tasks
jar.enabled = false

tasks.bootJar {
    dependsOn(copyHermesMessageProcessorDependency)
    exclude("dd-java-agent-*.jar")
}

val copyHermesMessageProcessorDependency by tasks.registering(Copy::class) {
    from(configurations.compileClasspath)
    into(project.layout.buildDirectory.dir("libs"))
    include("dd-java-agent-*.jar")
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
