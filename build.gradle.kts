import kotlinx.kover.api.CoverageEngine
import kotlinx.kover.api.KoverExtension

plugins {
    kotlin("jvm") version "1.6.10" apply false
    id("org.jetbrains.kotlinx.kover") version "0.5.0"
    id("org.sonarqube") version "3.3"
}

allprojects {
    group = "nl.marc"
    version = "0.1"
}

extensions.configure<KoverExtension> {
    coverageEngine.set(CoverageEngine.JACOCO)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.getByName("sonarqube") {
    dependsOn("koverMergedReport")
}

sonarqube {
    properties {
        property("sonar.projectKey", "Avans-DevOps")
        property("sonar.organization", "avansinformatica-marc")
        property("sonar.host.url", "https://sonarcloud.io")

        property("sonar.coverage.jacoco.xmlReportPaths", "${projectDir.invariantSeparatorsPath}/build/reports/kover/report.xml")
    }
}
