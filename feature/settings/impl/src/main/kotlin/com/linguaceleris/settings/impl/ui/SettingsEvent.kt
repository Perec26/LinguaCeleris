package com.linguaceleris.settings.impl.ui

internal sealed class SettingsEvent {
    data object OnBackPressed : SettingsEvent()
    data object OnUseSystemThemeClick : SettingsEvent()
    data object OnUseDarkThemeClick : SettingsEvent()
}
