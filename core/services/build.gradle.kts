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
    implementation(libs.androidx.datastore)
    api(libs.datetime)
}
