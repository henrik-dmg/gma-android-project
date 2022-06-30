// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navLib}")
        classpath ("com.android.tools.build:gradle:${Versions.gradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
    }
}

plugins {
    id("com.android.library") version Versions.gradle apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}