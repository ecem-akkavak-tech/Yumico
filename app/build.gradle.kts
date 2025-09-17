plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.safeargs) //veri transferi için
    alias(libs.plugins.hilt) // Hilt plugin
    kotlin("kapt") // kapt ekle
   // id("com.google.gms.google-services") version "4.4.3" //firestore için
}

android {
    namespace = "com.ecemm.yumico"
    compileSdk = 36
    buildFeatures{
        viewBinding = true
        dataBinding = true
    }

    defaultConfig {
        applicationId = "com.ecemm.yumico"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    // ViewModel & LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Location
    //implementation ("com.google.android.gms:play-services-location:21.2.0")
    //Map
    //implementation("com.google.android.gms:play-services-maps:19.2.0")
    //glide
    implementation(libs.glide)
    kapt(libs.compiler)
    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    //firestore için
    //implementation(platform("com.google.firebase:firebase-bom:34.2.0"))
    //implementation("com.google.firebase:firebase-firestore")
    // https://firebase.google.com/docs/android/setup#available-libraries
}