plugins {
    alias(libs.plugins.linguaceleris.android.application)
    alias(libs.plugins.linguaceleris.android.application.compose)
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
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    // Project
    implementation(projects.core.ui)
    implementation(projects.core.designsystem)


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
