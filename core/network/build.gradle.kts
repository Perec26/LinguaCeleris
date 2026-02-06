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
}
