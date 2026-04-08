package com.linguaceleris.home.impl.ui

import com.linguaceleris.home.impl.ui.model.StreakUI

internal data class HomeUiState(
    val isLoading: Boolean = true,
    val menuExpanded: Boolean = false,
    val streak: StreakUI = StreakUI.NeverStarted,
) {

    fun startLoading() = copy(isLoading = true)
    fun dataLoaded(streak: StreakUI) = copy(isLoading = false, streak = streak)

    fun onOpenMenuClick() = copy(menuExpanded = true)
    fun onCloseMenuClick() = copy(menuExpanded = false)
}
