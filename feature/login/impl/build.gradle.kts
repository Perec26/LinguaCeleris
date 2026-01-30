plugins {
    alias(libs.plugins.linguaceleris.android.feature)
}

android {
    namespace = "com.linguaceleris.login.impl"
}

dependencies {
    implementation(projects.feature.login.api)
}
