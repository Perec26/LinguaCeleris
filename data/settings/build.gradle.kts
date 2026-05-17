plugins {
    alias(libs.plugins.linguaceleris.data)
}

android {
    namespace = "com.linguaceleris.settings"
}

dependencies {
    implementation(projects.core.services)
}
