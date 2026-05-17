plugins {
    alias(libs.plugins.linguaceleris.data)
}

android {
    namespace = "com.linguaceleris.quiz"
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.services)
    implementation(libs.coil.network.okhttp)
}
