import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.compose.compiler)
    id("maven-publish")
}

android {
    namespace = "com.dreamsoftware.brownie"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidComposeCompiler.get()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

val githubProperties = Properties()
githubProperties.load(FileInputStream(rootProject.file("github.properties")))

publishing {
    publications {
        create<MavenPublication>("gpr") {
            run {
                groupId = "com.dreamsoftware.libraries"
                artifactId = "brownie-ui"
                version = "0.0.69"
                artifact("$buildDir/outputs/aar/app-release.aar")
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/sergio11/brownie_ui_library")
            credentials {
                username = githubProperties["gpr.usr"] as String? ?: System.getenv("GPR_USER")
                password = githubProperties["gpr.key"] as String? ?: System.getenv("GPR_API_KEY")
            }
        }
    }
}

dependencies {
    // Core Library Desugaring for enabling modern APIs on older versions of Android
    coreLibraryDesugaring(libs.core.library.desugaring)

    // AndroidX Core KTX to add Kotlin functionality to AndroidX core libraries
    implementation(libs.androidx.core.ktx)

    // AndroidX Activity Compose for using Compose in AndroidX activities
    implementation(libs.androidx.activity.compose)

    // Compose BOM (Bill of Materials) for managing the versions of Compose libraries
    implementation(platform(libs.compose.bom))
    implementation(libs.androidx.compose.material3)  // Material 3 components for Compose UI.
    implementation(libs.androidx.compose.material)  // Material Design components for Compose.
    implementation(libs.material.icons.extended)  // Extended set of Material Design icons for Compose.

    // ViewModel Compose for integrating ViewModel with Compose
    implementation(libs.androidx.lifecycle.viewModelCompose)

    // Lifecycle-aware components perform actions in response to a change in the lifecycle status of another component, such as activities and fragments. These components help you produce better-organized, and often lighter-weight code, that is easier to maintain.
    api(libs.lifecycle.runtime.compose)

    // Coil Compose for loading and displaying images in Compose
    implementation(libs.coil.compose)
}