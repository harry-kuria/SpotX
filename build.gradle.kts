plugins {
    id("com.android.application") version "8.6.1" apply false
    id("com.android.library") version "8.6.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.25" apply false
    id("org.jetbrains.dokka") version "1.9.20" apply false
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

allprojects {
    group = "io.github.harry-kuria"
    version = "0.1.0"
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
            snapshotRepositoryUrl.set(uri("https://central.sonatype.com/repository/maven-snapshots/"))
            packageGroup.set("io.github.harry-kuria")
            username.set(
                providers.gradleProperty("sonatypeUsername")
                    .orElse(providers.environmentVariable("OSSRH_USERNAME"))
            )
            password.set(
                providers.gradleProperty("sonatypePassword")
                    .orElse(providers.environmentVariable("OSSRH_PASSWORD"))
            )
        }
    }
}

subprojects {
    afterEvaluate {
        // Common JVM options
        extensions.findByName("kotlin")
    }
} 