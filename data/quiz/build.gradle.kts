plugins {
    alias(libs.plugins.linguaceleris.android.library)
    alias(libs.plugins.linguaceleris.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.linguaceleris.quiz"
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.services)
    implementation(libs.coil.network.okhttp)
}
