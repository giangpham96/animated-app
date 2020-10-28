object Versions {
    const val transition = "1.4.0-beta01"
    const val androidX = "1.1.0"
    const val constraintlayout = "1.1.3"
    const val coreKtx = "1.2.0"
    const val glide = "4.11.0"
    const val kotlin = "1.4.10"
    const val material = "1.1.0"
    const val recyclerView = "1.1.0"
    const val epoxy = "3.9.0"
    const val jsonSerialization = "1.0.0"
}

object Dependencies {
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.coreKtx}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.androidX}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val materialDesign = "com.google.android.material:material:${Versions.material}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val epoxy = "com.airbnb.android:epoxy:${Versions.epoxy}"
    const val epoxyProcessor = "com.airbnb.android:epoxy-processor:${Versions.epoxy}"
    const val transition = "androidx.transition:transition-ktx:${Versions.transition}"
    const val jsonSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.jsonSerialization}"
}
