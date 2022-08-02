plugins {
    id (BuildPlugin.androidApplication)
    id (BuildPlugin.kotlinAndroid)
}

val compose_version = Compose.version

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "io.github.rk22000.popquests"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = compose_version
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
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

    implementation(project(Modules.designSystems))
    implementation(project(Modules.data))
    implementation(project(Modules.basicQuests))

    Moshi.implementations.forEach {
        implementation(it)
    }

//    implementation ("androidx.core:core-ktx:1.8.0")
//    implementation ("androidx.compose.ui:ui:$compose_version")
//    implementation ("androidx.compose.material:material:$compose_version")
//    implementation ("androidx.compose.ui:ui-tooling-preview:$compose_version")
//    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0")
//    implementation ("androidx.activity:activity-compose:1.5.0")
//    testImplementation ("junit:junit:4.13.2")
//    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
//    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
//    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:$compose_version")
//    debugImplementation ("androidx.compose.ui:ui-tooling:$compose_version")
//    debugImplementation ("androidx.compose.ui:ui-test-manifest:$compose_version")
}