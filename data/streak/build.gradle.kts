plugins {
    alias(libs.plugins.linguaceleris.android.library)
    alias(libs.plugins.linguaceleris.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.linguaceleris.streak"
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.services)
    implementation(projects.common.time)
}
