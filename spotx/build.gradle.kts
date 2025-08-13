plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
	id("org.jetbrains.dokka")
	id("maven-publish")
	id("signing")
}

android {
	namespace = "io.github.harrykuria.spotx"
	compileSdk = 35

	defaultConfig {
		minSdk = 24
		consumerProguardFiles("consumer-rules.pro")
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

	publishing {
		singleVariant("release") {
			withSourcesJar()
			withJavadocJar()
		}
	}
}

dependencies {
	implementation(platform("androidx.compose:compose-bom:${project.findProperty("compose.bom.version") ?: "2024.10.00"}"))
	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-graphics")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3")
	implementation("androidx.compose.runtime:runtime")
	implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")
}

// Dokka HTML output to site/api
tasks.register<org.jetbrains.dokka.gradle.DokkaTask>("dokkaHtmlSite") {
	outputDirectory.set(layout.projectDirectory.dir("..")
		.dir("site").dir("api").asFile)
}

// Publishing configuration
afterEvaluate {
	publishing {
		publications {
			create<MavenPublication>("mavenRelease") {
				from(components["release"])
				groupId = project.group.toString()
				artifactId = "spotx"
				version = project.version.toString()
				pom {
					name.set("SpotX")
					description.set("Jetpack Compose onboarding tours SDK with spotlight overlays.")
					url.set("https://github.com/harry-kuria/SpotX")
					scm {
						url.set("https://github.com/harry-kuria/SpotX")
						connection.set("scm:git:https://github.com/harry-kuria/SpotX.git")
						developerConnection.set("scm:git:ssh://git@github.com/harry-kuria/SpotX.git")
					}
					licenses {
						license {
							name.set("Apache-2.0")
							url.set("https://www.apache.org/licenses/LICENSE-2.0")
						}
					}
					developers {
						developer {
							id.set("harry-kuria")
							name.set("Harry Kuria")
						}
					}
				}
			}
		}
		repositories {
			maven {
				name = "OSSRH"
				url = uri(if ((version as String).endsWith("SNAPSHOT"))
					"https://s01.oss.sonatype.org/content/repositories/snapshots/"
				else
					"https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
				credentials {
					username = findProperty("mavenCentralUsername") as String? ?: System.getenv("OSSRH_USERNAME")
					password = findProperty("mavenCentralPassword") as String? ?: System.getenv("OSSRH_PASSWORD")
				}
			}
		}
	}

	// Signing only if keys are available
	signing {
		val signingKey: String? = findProperty("signingInMemoryKey") as String?
		val signingPassword: String? = findProperty("signingInMemoryKeyPassword") as String?
		if (signingKey != null && signingPassword != null) {
			useInMemoryPgpKeys(signingKey, signingPassword)
			sign(publishing.publications["mavenRelease"])
		}
	}
} 