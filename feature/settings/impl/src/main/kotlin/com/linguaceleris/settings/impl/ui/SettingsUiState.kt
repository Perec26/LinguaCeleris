package com.linguaceleris.settings.impl.ui

internal data class SettingsUiState(
    val isLoading: Boolean = true,
    val useSystemTheme: Boolean = true,
    val useDarkTheme: Boolean = true,
) {
    fun updateUseSystemTheme(useSystemTheme: Boolean) = copy(useSystemTheme = useSystemTheme)

    fun updateUseDarkTheme(useDarkTheme: Boolean) = copy(useDarkTheme = useDarkTheme)

    fun settingsLoaded(useSystemTheme: Boolean, useDarkTheme: Boolean,) = copy(
        isLoading = false,
        useSystemTheme = useSystemTheme,
        useDarkTheme = useDarkTheme,
    )
}
