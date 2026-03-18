plugins {
    alias(libs.plugins.linguaceleris.feature.impl)
}

android {
    namespace = "com.linguaceleris.quiz.impl"
    resourcePrefix = "quiz_"
}

dependencies {
    implementation(libs.androidx.media3)
    implementation(projects.core.media)
    implementation(projects.data.quiz)
    implementation(projects.feature.quiz.api)
    implementation(projects.feature.quizSummary.api)
}
