object Dependencies {

    val kotlin by lazy {
        "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    }
    val coreKTX by lazy {
        "androidx.core:core-ktx:${Versions.coreKTX}"
    }
    val material by lazy {
        "com.google.android.material:material:1.6.1"
    }
    val appCompat by lazy {
        "androidx.appcompat:appcompat:1.4.2"
    }
    val constraintLayout by lazy {
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    }
    val liveData by lazy {
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleLib}"
    }
    val viewModel by lazy {
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleLib}"
    }
    val navigationFragment by lazy {
        "androidx.navigation:navigation-fragment-ktx:${Versions.navLib}"
    }
    val navigationUI by lazy {
        "androidx.navigation:navigation-ui-ktx:${Versions.navLib}"
    }
    val junit by lazy {
        "junit:junit:${Versions.jUnit}"
    }
    val junitAndroid by lazy {
        "androidx.test.ext:junit:1.1.3"
    }
    val espresso by lazy {
        "androidx.test.espresso:espresso-core:${Versions.espresso}"
    }

    val appDependencies: List<String>
        get() {
            return listOf(
                kotlin,
                material,
                coreKTX,
                appCompat,
                constraintLayout,
                liveData,
                viewModel,
                navigationFragment,
                navigationUI
            )
        }

    val testDependencies: List<String>
        get() {
            return listOf(junit)
        }

    val androidTestDependencies: List<String>
        get() {
            return listOf(
                espresso,
                junitAndroid
            )
        }

}