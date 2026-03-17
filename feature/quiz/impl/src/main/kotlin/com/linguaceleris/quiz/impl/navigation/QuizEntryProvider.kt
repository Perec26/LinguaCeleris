package com.linguaceleris.quiz.impl.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.linguaceleris.quiz.api.QuizNavKey
import com.linguaceleris.quiz.impl.ui.QuizScreen
import com.linguaceleris.quiz.impl.ui.QuizViewModel

fun EntryProviderScope<NavKey>.quizEntry() {
    entry<QuizNavKey> { key ->
        val viewModel = hiltViewModel<QuizViewModel, QuizViewModel.Factory>(
            key = key.quizId,
        ) {
            it.create(key.quizId)
        }
        QuizScreen(viewModel)
    }
}
