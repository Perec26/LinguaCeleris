plugins {
    alias(libs.plugins.linguaceleris.android.application)
    alias(libs.plugins.linguaceleris.android.application.compose)
    alias(libs.plugins.kover)
}

android {
    namespace = "com.linguaceleris"

    defaultConfig {
        applicationId = "com.linguaceleris"
        versionCode = getBuildNumber()
        versionName = "0.0." + getBuildNumber()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.coil.compose)
    implementation(libs.androidx.credentials)
    implementation(libs.android.googleid)

    // Project
    implementation(projects.core.ui)
    implementation(projects.core.designsystem)
    implementation(projects.core.navigation)
    implementation(projects.data.auth)
    implementation(projects.data.settings)
    implementation(projects.feature.auth.impl)
    implementation(projects.feature.home.impl)
    implementation(projects.feature.settings.impl)
    implementation(projects.feature.start.api)
    implementation(projects.feature.start.impl)
    implementation(projects.feature.quiz.impl)
}

fun getBuildNumber(): Int {
    if (project.hasProperty("buildNumber")) {
        val buildNumberString = project.property("buildNumber").toString()
        if (buildNumberString.all { it.isDigit() }) {
            return buildNumberString.toInt()
        }
    }
    return 1
}
