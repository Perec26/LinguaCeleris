package com.linguaceleris.quizselection.impl.ui

import com.linguaceleris.quizselection.impl.ui.model.DayQuizzesUI

internal data class QuizSelectionUiState(
    val dayQuizzes: DayQuizzesUI = DayQuizzesUI(),
    val isLoading: Boolean = true,
) {
    fun quizzesLoaded(dayQuizzes: DayQuizzesUI) = copy(dayQuizzes = dayQuizzes, isLoading = false)
}
