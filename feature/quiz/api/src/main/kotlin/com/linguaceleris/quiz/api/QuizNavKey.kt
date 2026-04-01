package com.linguaceleris.quiz.api

import androidx.navigation3.runtime.NavKey
import com.linguaceleris.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
data class QuizNavKey(val quizId: String) : NavKey

fun Navigator.navigateToQuiz(quizId: String) = navigateTo(QuizNavKey(quizId))
