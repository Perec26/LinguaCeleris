plugins {
    alias(libs.plugins.linguaceleris.android.library)
    alias(libs.plugins.linguaceleris.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.linguaceleris.network"
}

dependencies {
    api(platform(libs.firebase.bom))
    api(libs.firebase.firestore)
    api(libs.kotlinx.serialization.json)
    implementation(libs.coil.network.okhttp)
    implementation(libs.androidx.media3)
    implementation(libs.androidx.credentials)
    implementation(libs.android.googleid)
    implementation(libs.firebase.auth)
    implementation(libs.datetime)
}
