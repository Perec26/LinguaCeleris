package com.linguaceleris.home.impl.ui

import com.linguaceleris.home.impl.ui.model.DayQuizzesUI

internal data class HomeUiState(
    val isLoading: Boolean = true,
    val dayQuizzes: DayQuizzesUI = DayQuizzesUI(),
    val menuExpanded: Boolean = false,

) {

    fun quizzesLoaded(dayQuizzes: DayQuizzesUI) = copy(dayQuizzes = dayQuizzes, isLoading = false)

    fun onOpenMenuClick() = copy(menuExpanded = true)
    fun onCloseMenuClick() = copy(menuExpanded = false)
}
