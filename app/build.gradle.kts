plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.hiltPlugin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "com.davidluna.liverpool"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.davidluna.liverpool"
        minSdk = 26
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.icons.extended)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.retrofit)
    implementation(libs.okhttp.client)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.kotlin.converter)
    implementation(libs.kotlinxSerializationJson)
    implementation(libs.arrow.core)
    implementation(libs.paging.compose)
    implementation(libs.paging.runtime)
    implementation(libs.coil.compose)
    implementation(libs.hilt.android)
    debugImplementation(libs.ui.tooling)
    ksp(libs.hilt.compiler)
}