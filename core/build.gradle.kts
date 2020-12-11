plugins {
    id("com.android.library")
    id("kotlin-android")
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":app-resource"))
    implementation(project(":core-animation"))
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.materialDesign)
    implementation(Dependencies.fragmentKtx)
    implementation(Dependencies.transition)
    implementation(Dependencies.exoplayer)
}
