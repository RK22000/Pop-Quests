plugins {
}

    android {
    compileSdk = 32

    defaultConfig {
      applicationId = "io.github.rk22000.myapplication"
    minSdk = 21
    targetSdk = 32
    versionCode = 1
    versionName = "1.0"

      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
       release {
           isMinifyEnabled = false
           proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
       }
    }
    }

  dependencies {

  }