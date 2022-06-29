plugins {
    id("com.android.application")
    kotlin("android")
    id("androidx.navigation.safeargs.kotlin")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    viewBinding {
        android.buildFeatures.viewBinding = true
    }

    namespace = "htw.gma_sose22"
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")

    implementation("com.google.android.material:material:1.6.1")

    implementation("androidx.core:core-ktx:${Versions.coreKTX}")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleLib}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleLib}")
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.navLib}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.navLib}")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

}