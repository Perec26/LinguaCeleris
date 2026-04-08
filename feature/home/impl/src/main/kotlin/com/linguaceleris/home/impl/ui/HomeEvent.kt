package com.linguaceleris.home.impl.ui

internal sealed class HomeEvent {
    data object OnEastQuizClick : HomeEvent()
    data object OnMediumQuizClick : HomeEvent()
    data object OnHardQuizClick : HomeEvent()
    data object OnOpenMenuClick : HomeEvent()
    data object OnCloseMenuClick : HomeEvent()
    data object OnSettingsClick : HomeEvent()
    data object OnSignOutClick : HomeEvent()
}
