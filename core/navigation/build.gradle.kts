plugins {
    alias(libs.plugins.linguaceleris.android.library)
}

android {
    namespace = "com.linguaceleris.navigation"
}

dependencies {
    api(libs.androidx.navigation3.runtime)
    // Todo: посмотреть надо ли оно
    implementation(libs.androidx.savedstate.compose)
}
