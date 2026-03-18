plugins {
    alias(libs.plugins.linguaceleris.feature.impl)
}

android {
    namespace = "com.linguaceleris.quizsummary.impl"
    resourcePrefix = "quiz_summary_"
}

dependencies {
    implementation(projects.feature.quizSummary.api)
}
