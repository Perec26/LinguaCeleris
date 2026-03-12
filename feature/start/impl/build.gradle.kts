plugins {
    alias(libs.plugins.linguaceleris.android.feature)
}

android {
    namespace = "com.linguaceleris.start.impl"
    resourcePrefix = "start_"
}

dependencies {
    implementation(projects.feature.start.api)
    implementation(projects.feature.login.api)
    implementation(projects.feature.quiz.api)
    implementation(projects.data.quiz)
}
