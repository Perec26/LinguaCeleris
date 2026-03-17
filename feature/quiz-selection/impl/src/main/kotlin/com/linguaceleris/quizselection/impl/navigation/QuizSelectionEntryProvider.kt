package com.linguaceleris.quizselection.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.linguaceleris.quizselection.api.QuizSelectionNavKey
import com.linguaceleris.quizselection.impl.ui.QuizSelectionScreen

fun EntryProviderScope<NavKey>.quizSelectionEntry() {
    entry<QuizSelectionNavKey> { _ -> QuizSelectionScreen() }
}
