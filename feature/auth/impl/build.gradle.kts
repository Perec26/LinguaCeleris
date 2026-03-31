plugins {
    alias(libs.plugins.linguaceleris.feature.impl)
}

android {
    namespace = "com.linguaceleris.auth.impl"
    resourcePrefix = "auth_"
}

dependencies {
    implementation(projects.feature.auth.api)
    implementation(projects.feature.home.api)
    implementation(projects.core.network)
    implementation(projects.data.auth)
    implementation(libs.androidx.credentials.play.services)
    implementation(libs.android.googleid)
    implementation(libs.firebase.auth)
}
