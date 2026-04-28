package com.linguaceleris.home.impl.ui

internal sealed class HomeEvent {
    data object OnBasicQuizClick : HomeEvent()
    data object OnIntermediateQuizClick : HomeEvent()
    data object OnAdvanceQuizClick : HomeEvent()
    data object OnOpenMenuClick : HomeEvent()
    data object OnCloseMenuClick : HomeEvent()
    data object OnSettingsClick : HomeEvent()
    data object OnSignOutClick : HomeEvent()
    data object OnYoutubeClick : HomeEvent()
    data object OnTelegramClick : HomeEvent()
    data object OnRefreshClick : HomeEvent()
}
