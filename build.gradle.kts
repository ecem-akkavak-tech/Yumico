// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false //  Hilt plugin
   // id("com.google.gms.google-services") version "4.4.3" apply false //firestore
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false // map
}
