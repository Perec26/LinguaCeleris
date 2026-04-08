package com.linguaceleris.quiz.impl.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quiz.api.QuizDifficulty
import com.linguaceleris.quiz.api.QuizNavKey
import com.linguaceleris.quiz.impl.ui.quiz.QuizScreen
import com.linguaceleris.quiz.impl.ui.quiz.QuizViewModel
import com.linguaceleris.quiz.impl.ui.summary.QuizSummaryScreen
import com.linguaceleris.quiz.impl.ui.summary.QuizSummaryViewModel
import kotlinx.serialization.Serializable

@Serializable
data class QuizSummaryNavKey(val difficulty: QuizDifficulty, val isSuccessful: Boolean) : NavKey

fun EntryProviderScope<NavKey>.quizEntry() {
    entry<QuizNavKey> { key ->
        val viewModel = hiltViewModel<QuizViewModel, QuizViewModel.Factory>(
            key = key.quizDifficulty.toString(),
        ) {
            it.create(key.quizDifficulty)
        }
        QuizScreen(viewModel)
    }

    entry<QuizSummaryNavKey> { key ->
        val viewModel = hiltViewModel<QuizSummaryViewModel, QuizSummaryViewModel.Factory>(
            key = key.isSuccessful.toString(),
        ) {
            it.create(key.difficulty, key.isSuccessful)
        }
        QuizSummaryScreen(viewModel)
    }
}

internal fun Navigator.navigateToSummary(difficulty: QuizDifficulty, isSuccessful: Boolean) =
    replace(QuizSummaryNavKey(difficulty, isSuccessful))
