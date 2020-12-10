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
}

dependencies {
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.materialDesign)
}
