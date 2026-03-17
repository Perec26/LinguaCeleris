plugins {
    alias(libs.plugins.linguaceleris.android.library)
    alias(libs.plugins.linguaceleris.hilt)
}

android {
    namespace = "com.linguaceleris.services"
}

dependencies {
    implementation(libs.playservices.time)
    implementation(libs.coroutines.playservices)
    api(libs.datetime)
}
