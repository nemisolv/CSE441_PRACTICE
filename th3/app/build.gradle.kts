plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "net.nemisolv.recyclerview"
    compileSdk = 34

    defaultConfig {
        applicationId = "net.nemisolv.recyclerview"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

implementation("com.google.code.gson:gson:2.8.9")
//import jackson
//    implementation("com.fasterxml.jackson.core:jackson-core:2.13.0")
//    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
//    implementation("com.fasterxml.jackson.core:jackson-annotations:2.13.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}