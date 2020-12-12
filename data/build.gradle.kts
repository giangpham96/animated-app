plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.4.10"
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(30)
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":app-resource"))
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.jsonSerialization)
}
