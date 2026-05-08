package com.linguaceleris.home.api

import androidx.navigation3.runtime.NavKey
import com.linguaceleris.navigation.NavResult
import com.linguaceleris.navigation.Navigator
import kotlinx.serialization.Serializable

const val QUIZ_RESULT_KEY = "quizResult"

@Serializable
data object HomeNavKey : NavKey

data class QuizResult(override val requestKey: String = QUIZ_RESULT_KEY) : NavResult

fun Navigator.startWithHome() = startWith(HomeNavKey)

suspend fun Navigator.backToHomeWithResult() = backToWithResult(
    destination = HomeNavKey,
    result = QuizResult(),
)
