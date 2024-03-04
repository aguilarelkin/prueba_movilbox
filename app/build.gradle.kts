plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.app.movilbox"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.movilbox"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
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
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val dagger = "2.48"
    val retroGson = "2.9.0"
    val navigation = "2.7.7"
    val lifecycle = "2.6.2"
    val okhtt = "4.3.1"
    val room = "2.4.3"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.05.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    //Navigation
    implementation("androidx.navigation:navigation-compose:${navigation}")

    //DaggerHilt
    implementation("com.google.dagger:hilt-android:${dagger}")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    kapt("com.google.dagger:hilt-compiler:${dagger}")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:${retroGson}")
    implementation("com.squareup.retrofit2:converter-gson:${retroGson}")
    implementation("com.squareup.okhttp3:logging-interceptor:${okhtt}")
    implementation("io.coil-kt:coil-compose:2.4.0")

    //ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${lifecycle}")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:${lifecycle}")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:${lifecycle}")

    //Room
    implementation ("androidx.room:room-runtime:${room}")
    implementation ("androidx.room:room-ktx:${room}")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}