// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    val lifecycle_version = "2.4.1"
    val gradle_version = "7.2.1"
    val kotlin_version = "1.7.0"
    val supportLibVersion = "25.3.0"
    val navLibVersion = "2.4.2"
    val room_version = "2.4.2"

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navLibVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}

plugins {
    id("com.android.application") version "7.2.1" apply false
    id("com.android.library") version "7.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}