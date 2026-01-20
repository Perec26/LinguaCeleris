plugins {
    alias(libs.plugins.linguaceleris.android.library)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.linguaceleris.ui"
}

dependencies {
    implementation(libs.androidx.lifecycle.viewModelCompose)
}