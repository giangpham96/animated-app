plugins {
    id ("java-library")
    id ("kotlin")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.4.10"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.jsonSerialization)
}
