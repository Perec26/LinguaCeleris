plugins {
    alias(libs.plugins.linguaceleris.android.library)
    alias(libs.plugins.linguaceleris.hilt)
}

android {
    namespace = "com.linguaceleris.media"
}

dependencies {
    implementation(libs.androidx.media3)
}
