plugins {
    alias(libs.plugins.linguaceleris.feature.impl)
}

android {
    namespace = "com.linguaceleris.home.impl"
    resourcePrefix = "home_"
}

dependencies {
    implementation(projects.feature.auth.api)
    implementation(projects.feature.home.api)
    implementation(projects.feature.settings.api)
    implementation(projects.feature.quiz.api)
    implementation(projects.core.services)
    implementation(projects.data.quiz)
    implementation(projects.data.auth)
}
