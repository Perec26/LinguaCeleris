plugins {
    alias(libs.plugins.linguaceleris.android.library)
    alias(libs.plugins.linguaceleris.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.linguaceleris.auth"
}

dependencies {
    implementation(projects.core.network)
}
