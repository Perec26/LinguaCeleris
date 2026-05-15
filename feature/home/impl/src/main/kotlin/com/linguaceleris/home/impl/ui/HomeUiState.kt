package com.linguaceleris.home.impl.ui

import com.linguaceleris.home.impl.ui.model.StreakUI
import com.linguaceleris.ui.ScreenState
import com.linguaceleris.ui.utils.UiText

internal data class HomeUiState(
    val screenState: ScreenState = ScreenState.LOADING,
    val menuExpanded: Boolean = false,
    val streak: StreakUI = StreakUI.NeverStarted,
    val nextQuizzesTimer: UiText = UiText.DynamicString(""),
    val lastHour: Boolean = false,
    val isGuest: Boolean = false,
) {

    val needToAlarm =
        lastHour && (streak is StreakUI.TodayNotCompleted || streak is StreakUI.Freeze)

    fun dataLoaded(streak: StreakUI, isGuest: Boolean) = copy(
        screenState = ScreenState.CONTENT,
        streak = streak,
        isGuest = isGuest,
    )

    fun onOpenMenuClick() = copy(menuExpanded = true)
    fun onCloseMenuClick() = copy(menuExpanded = false)

    fun updateNextQuizzesTimer(timer: UiText, lastHour: Boolean) = copy(
        nextQuizzesTimer = timer,
        lastHour = lastHour,
    )

    fun onLoading() = copy(screenState = ScreenState.LOADING)

    fun onError() = copy(screenState = ScreenState.ERROR)
}
