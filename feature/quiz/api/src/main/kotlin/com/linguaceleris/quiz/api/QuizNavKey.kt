package com.linguaceleris.quiz.api

import androidx.navigation3.runtime.NavKey
import com.linguaceleris.navigation.Navigator
import kotlinx.serialization.Serializable

enum class QuizLevel {
    BASIC,
    INTERMEDIATE,
    ADVANCED
}

@Serializable
data class QuizNavKey(val quizLevel: QuizLevel) : NavKey

fun Navigator.navigateToBasicQuiz() = navigateTo(QuizNavKey(QuizLevel.BASIC))
fun Navigator.navigateToIntermediateQuiz() = navigateTo(QuizNavKey(QuizLevel.INTERMEDIATE))
fun Navigator.navigateToAdvanceQuiz() = navigateTo(QuizNavKey(QuizLevel.ADVANCED))

fun Navigator.replaceWithQuiz(quizLevel: QuizLevel) = replace(QuizNavKey(quizLevel))
