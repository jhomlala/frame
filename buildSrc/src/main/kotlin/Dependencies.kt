import org.gradle.api.JavaVersion

object Config {
    val minSdk = 23
    val compileSdk = 28
    val targetSdk = 28
    val javaVersion = JavaVersion.VERSION_1_8
    val buildTools = "28.0.3"
}

object Version {
    val androidx_core = "1.0.1"
    val androidx_recyclerview = "1.0.0"
    val androidx_navigation = "2.0.0"
    val androidx_constraintLayout = "1.1.3"
    val material = "1.1.0-alpha04"
    val lifecycle = "2.1.0"
    val androidx_appcompat = "1.1.0"

    val junit = "4.12"
    val androidx_espresso = "3.1.0"
    val androidx_testing = "1.1.1"

    val gradleandroid = "3.5.0"
    val kotlin = "1.3.20"
    val gradleversions = "0.21.0"

    val retrofit = "2.6.2"
    val coroutines = "1.3.2"
    val okhttp = "4.2.1"
    val koin = "2.0.1"
    val gson = "2.8.6"
    val timber = "4.7.1"
    val glide = "4.10.0"
    val glide_transformations = "4.1.0"

}

object Deps {
    val androidx_core = "androidx.core:core-ktx:${Version.androidx_core}"
    val androidx_constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Version.androidx_constraintLayout}"
    val androidx_material = "com.google.android.material:material:${Version.material}"
    val androidx_navigation_fragment =
        "androidx.navigation:navigation-fragment-ktx:${Version.androidx_navigation}"
    val androidx_navigation_ui =
        "androidx.navigation:navigation-ui-ktx:${Version.androidx_navigation}"
    val androidx_recyclerview =
        "androidx.recyclerview:recyclerview:${Version.androidx_recyclerview}"
    val androidx_appcompat = "androidx.appcompat:appcompat:${Version.androidx_appcompat}"

    val testlib_junit = "junit:junit:${Version.junit}"

    val testandroidx_rules = "androidx.test:rules:${Version.androidx_testing}"
    val testandroidx_runner = "androidx.test:runner:${Version.androidx_testing}"
    val testandroidx_espressocore =
        "androidx.test.espresso:espresso-core:${Version.androidx_espresso}"

    val tools_gradleandroid = "com.android.tools.build:gradle:${Version.gradleandroid}"
    val tools_kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
    val tools_gradleversions =
        "com.github.ben-manes:gradle-versions-plugin:${Version.gradleversions}"

    val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    val retrofit_gson_converter = "com.squareup.retrofit2:converter-gson:${Version.retrofit}"
    val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"
    val coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"

    val okhttp = "com.squareup.okhttp3:okhttp:${Version.okhttp}"
    val okhttp_interceptor = "com.squareup.okhttp3:logging-interceptor:${Version.okhttp}"
    val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Version.lifecycle}"

    val koin_android = "org.koin:koin-android:${Version.koin}"
    val koin_android_scope = "org.koin:koin-androidx-scope:${Version.koin}"
    val koin_android_viewmodel = "org.koin:koin-androidx-viewmodel:${Version.koin}"
    val gson = "com.google.code.gson:gson:${Version.gson}"
    val timber = "com.jakewharton.timber:timber:${Version.timber}"
    val glide = "com.github.bumptech.glide:glide:${Version.glide}"
    val glide_compiler = "com.github.bumptech.glide:compiler:${Version.glide}"
    val glide_transformations = "jp.wasabeef:glide-transformations:${Version.glide_transformations}"
}
