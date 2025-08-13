plugins {
    id("com.android.application") version "8.5.2" apply false
    id("com.android.library") version "8.5.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
    id("org.jetbrains.dokka") version "1.9.20" apply false
}

allprojects {
    group = "io.github.harrykuria"
    version = "0.1.0"
}

subprojects {
    afterEvaluate {
        // Common JVM options
        extensions.findByName("kotlin")
    }
} 