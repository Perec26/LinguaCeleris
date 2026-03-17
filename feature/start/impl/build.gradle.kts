plugins {
    alias(libs.plugins.linguaceleris.feature.impl)
}

android {
    namespace = "com.linguaceleris.start.impl"
    resourcePrefix = "start_"
}

dependencies {
    implementation(projects.feature.start.api)
    implementation(projects.feature.login.api)
    implementation(projects.feature.quizSelection.api)
    implementation(projects.data.quiz)
}
