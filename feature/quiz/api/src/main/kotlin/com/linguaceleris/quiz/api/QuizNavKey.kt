package com.linguaceleris.quiz.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class QuizNavKey(
    val quizId: String
) : NavKey
