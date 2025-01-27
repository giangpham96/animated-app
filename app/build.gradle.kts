plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "la.me.leo.animatedapp"
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":tabs"))
    implementation(project(":data"))
    implementation(project(":onboarding"))
    implementation(project(":app-resource"))
    implementation(project(":core-animation"))
    implementation(project(":venue-detail-page"))
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.fragmentKtx)
}
