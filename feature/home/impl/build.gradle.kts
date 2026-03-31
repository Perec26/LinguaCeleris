plugins {
    alias(libs.plugins.linguaceleris.feature.impl)
}

android {
    namespace = "com.linguaceleris.home.impl"
    resourcePrefix = "home_"
}

dependencies {
    implementation(projects.feature.home.api)
    implementation(projects.feature.quiz.api)
}
