plugins {
    alias(libs.plugins.linguaceleris.android.library)
    alias(libs.plugins.linguaceleris.hilt)
}

android {
    namespace = "com.linguaceleris.settings"
}

dependencies {
    implementation(projects.core.services)
}
