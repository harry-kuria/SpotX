plugins {
    id("com.android.application") version "8.6.1" apply false
    id("com.android.library") version "8.6.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.25" apply false
    id("org.jetbrains.dokka") version "1.9.20" apply false
    id("com.vanniktech.maven.publish") version "0.34.0" apply false
}

allprojects {
    group = "io.github.harry-kuria"
    version = "0.1.0"
}

// Publishing is configured in module build files via Vanniktech Maven Publish

subprojects {
    afterEvaluate {
        // Common JVM options
        extensions.findByName("kotlin")
    }
} 