package com.linguaceleris.quiz.api

import androidx.navigation3.runtime.NavKey
import com.linguaceleris.navigation.Navigator
import kotlinx.serialization.Serializable

enum class QuizDifficulty {
    BASIC,
    INTERMEDIATE,
    ADVANCED
}

@Serializable
data class QuizNavKey(val quizDifficulty: QuizDifficulty) : NavKey

fun Navigator.navigateToBasicQuiz() = navigateTo(QuizNavKey(QuizDifficulty.BASIC))
fun Navigator.navigateToIntermediateQuiz() = navigateTo(QuizNavKey(QuizDifficulty.INTERMEDIATE))
fun Navigator.navigateToAdvanceQuiz() = navigateTo(QuizNavKey(QuizDifficulty.ADVANCED))
