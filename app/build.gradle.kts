import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlinx.serializer)
    id("kotlin-kapt")

}

android {
    namespace = "com.example.barcateam"
    compileSdk = 34

    val secretsProperties = Properties()
    val secretsFile = rootProject.file("secret.properties")
    if (secretsFile.exists()) {
        secretsProperties.load(secretsFile.inputStream())
    } else {
        throw GradleException("Where's the secret properties file?")
    }


    defaultConfig {
        applicationId = "com.example.barcateam"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "FOOTBALL_API_KEY", "${secretsProperties["FOOTBALL_API_KEY"]}")
        buildConfigField("String", "FOOTBALL_HOST_URL", "${secretsProperties["FOOTBALL_HOST_URL"]}")

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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.android.navigation.compose)
    kapt(libs.hilt.compiler)


    // network
    implementation(libs.retrofit)
    implementation(libs.okhttp.client)
    implementation(libs.okhttp.loggingInterceptor)
    implementation(libs.retrofit.converter.kotlinx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.core)

    // image
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.core)

    // Test
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}