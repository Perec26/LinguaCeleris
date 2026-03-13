plugins {
    alias(libs.plugins.linguaceleris.feature.impl)
}

android {
    namespace = "com.linguaceleris.login.impl"
    resourcePrefix = "login_"
}

dependencies {
    implementation(projects.feature.login.api)
}
