plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
}

rootProject.extra.apply {
    set("PUBLISH_GROUP_ID", "io.github.corca-ai")
    set("PUBLISH_ARTIFACT_ID", "adcio_analytics")
    set("PUBLISH_VERSION", "1.4.5")
    set("PUBLISH_SCM_URL", "https://github.com/corca-ai/adcio_android_plugins")
    set("PUBLISH_DESCRIPTION", "adcio_analytics is log event collection")
}

android {
    namespace = "ai.corca.adcio_analytics"
    compileSdk = 33

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

apply(from = "${rootProject.projectDir}/scripts/publish-module.gradle")

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    implementation(project(path = ":core"))
}
