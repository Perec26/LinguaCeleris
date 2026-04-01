package com.linguaceleris.home.impl.ui

internal sealed class HomeEvent {
    data class OnQuizClick(val quizId: String) : HomeEvent()
    data object OnOpenMenuClick : HomeEvent()
    data object OnCloseMenuClick : HomeEvent()
    data object OnSettingsClick : HomeEvent()
    data object OnSignOutClick : HomeEvent()
}
