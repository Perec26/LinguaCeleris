package com.linguaceleris.home.impl.ui

import com.linguaceleris.home.impl.ui.model.StreakUI
import com.linguaceleris.ui.utils.UiText

internal data class HomeUiState(
    val isLoading: Boolean = true,
    val menuExpanded: Boolean = false,
    val streak: StreakUI = StreakUI.NeverStarted,
    val nextQuizzesTimer: UiText = UiText.DynamicString(""),
    val lastHour: Boolean = false,
) {

    val needToAlarm =
        lastHour && (streak is StreakUI.TodayNotCompleted || streak is StreakUI.Freeze)

    fun startLoading() = copy(isLoading = true)
    fun dataLoaded(streak: StreakUI) = copy(isLoading = false, streak = streak)

    fun onOpenMenuClick() = copy(menuExpanded = true)
    fun onCloseMenuClick() = copy(menuExpanded = false)

    fun updateNextQuizzesTimer(timer: UiText, lastHour: Boolean) = copy(
        nextQuizzesTimer = timer,
        lastHour = lastHour,
    )
}
