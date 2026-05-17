plugins {
    alias(libs.plugins.linguaceleris.data)
}

android {
    namespace = "com.linguaceleris.streak"
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.services)
    implementation(projects.common.time)
}
