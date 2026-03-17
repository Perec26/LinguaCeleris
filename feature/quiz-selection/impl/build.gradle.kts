plugins {
    alias(libs.plugins.linguaceleris.feature.impl)
}

android {
    namespace = "com.linguaceleris.quizselection.impl"
    resourcePrefix = "quiz_selection_"
}

dependencies {
    implementation(projects.core.services)
    implementation(projects.data.quiz)
    implementation(projects.feature.quizSelection.api)
    implementation(projects.feature.quiz.api)
}
