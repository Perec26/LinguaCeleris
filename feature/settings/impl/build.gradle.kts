plugins {
    alias(libs.plugins.linguaceleris.feature.impl)
}

android {
    namespace = "com.linguaceleris.settings.impl"
    resourcePrefix = "settings_"
}

dependencies {
    implementation(projects.feature.settings.api)
    implementation(projects.data.settings)
    implementation(projects.core.config)
}
