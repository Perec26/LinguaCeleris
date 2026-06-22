plugins {
    alias(libs.plugins.linguaceleris.android.library.compose)
}

android {
    namespace = "com.linguaceleris.designsystem"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    api(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.windowsize)
    debugImplementation(libs.androidx.ui.tooling)
}
