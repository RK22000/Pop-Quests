plugins {
    id(BuildPlugin.androidLibrary)
    id(BuildPlugin.kotlinAndroid)
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 26
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//      consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    Dependencies.implementations.forEach {
        implementation(it)
    }
    Dependencies.androidTestImplementations.forEach {
        androidTestImplementation(it)
    }
    Dependencies.testImplementations.forEach {
        testImplementation(it)
    }
    Dependencies.debugImplementations.forEach {
        debugImplementation(it)
    }

    Moshi.implementations.forEach {
        implementation(it)
    }

}