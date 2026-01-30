plugins {
    alias(libs.plugins.linguaceleris.android.feature)
}

android {
    namespace = "com.linguaceleris.start.impl"
}

dependencies {
    implementation(projects.feature.start.api)
    implementation(projects.feature.login.api)
}
