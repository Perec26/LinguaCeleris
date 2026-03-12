package com.linguaceleris.quiz.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.linguaceleris.quiz.api.QuizNavKey
import com.linguaceleris.quiz.impl.ui.QuizScreen

fun EntryProviderScope<NavKey>.quizEntry() {
    entry<QuizNavKey> { _ -> QuizScreen() }
}
