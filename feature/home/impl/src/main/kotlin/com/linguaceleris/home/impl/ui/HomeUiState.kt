package com.linguaceleris.home.impl.ui

import com.linguaceleris.home.impl.ui.model.DayQuizzesUI

internal data class HomeUiState(
    val isLoading: Boolean = true,
    val dayQuizzes: DayQuizzesUI = DayQuizzesUI(),
) {

    fun quizzesLoaded(dayQuizzes: DayQuizzesUI) = copy(dayQuizzes = dayQuizzes, isLoading = false)
}
