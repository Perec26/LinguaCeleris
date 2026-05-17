plugins {
    alias(libs.plugins.linguaceleris.data)
}

android {
    namespace = "com.linguaceleris.auth"
}

dependencies {
    implementation(projects.core.network)
}
