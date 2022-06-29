plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs")
    kotlin("android")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "htw.gma_sose22.metronomepro"
        minSdk = 28
        targetSdk = 32
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding {
        android.buildFeatures.viewBinding = true
    }

    namespace = "htw.gma_sose22"
}

dependencies {

    val lifecycle_version = "2.4.1"
    val gradle_version = "7.2.1"
    val kotlin_version = "1.7.0"
    val supportLibVersion = "25.3.0"
    val navLibVersion = "2.4.2"
    val room_version = "2.4.2"

    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")

    implementation("com.google.android.material:material:1.6.1")

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$room_version")

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.navigation:navigation-fragment-ktx:$navLibVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navLibVersion")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

}