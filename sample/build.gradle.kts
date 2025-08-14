plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
}

android {
	namespace = "io.github.harrykuria.spotx.sample"
	compileSdk = 35

	defaultConfig {
		applicationId = "io.github.harrykuria.spotx.sample"
		minSdk = 24
		targetSdk = 35
		versionCode = 1
		versionName = "1.0"
	}

	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = project.findProperty("compose.compiler.version") as String? ?: "1.5.15"
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = "17"
	}
}

dependencies {
	implementation(platform("androidx.compose:compose-bom:2024.09.02"))
	implementation(project(":spotx"))
	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-graphics")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3")
	implementation("androidx.activity:activity-compose:1.9.2")
	implementation("com.google.android.material:material:1.12.0")
	debugImplementation("androidx.compose.ui:ui-tooling")
} 