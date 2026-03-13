plugins {
    alias(libs.plugins.linguaceleris.feature.impl)
}

android {
    namespace = "com.linguaceleris.quiz.impl"
    resourcePrefix = "quiz_"
}

dependencies {
    implementation(projects.data.quiz)
    implementation(projects.feature.quiz.api)
}
