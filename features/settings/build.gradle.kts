plugins {
    id(BuildPlugin.androidLibrary)
    id(BuildPlugin.kotlinAndroid)
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.version
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
    implementation(project(Modules.data))
    implementation(project(Modules.designSystems))
    implementation(project(Modules.navigation))
}
