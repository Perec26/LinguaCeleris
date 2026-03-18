package com.linguaceleris.quizsummary.impl.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.linguaceleris.quizsummary.api.QuizSummaryNavKey
import com.linguaceleris.quizsummary.impl.ui.QuizSummaryScreen
import com.linguaceleris.quizsummary.impl.ui.QuizSummaryViewModel

fun EntryProviderScope<NavKey>.quizSummaryEntry() {
    entry<QuizSummaryNavKey> { key ->
        val viewModel = hiltViewModel<QuizSummaryViewModel, QuizSummaryViewModel.Factory>(
            key = key.isSuccessful.toString(),
        ) {
            it.create(key.isSuccessful)
        }
        QuizSummaryScreen(viewModel)
    }
}
