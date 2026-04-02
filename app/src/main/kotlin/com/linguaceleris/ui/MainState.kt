package com.linguaceleris.ui

data class MainState(val useSystemTheme: Boolean = true, val useDarkTheme: Boolean = true,) {
    fun updateUseSystemTheme(useSystemTheme: Boolean) = copy(useSystemTheme = useSystemTheme)
    fun updateUseDarkTheme(useDarkTheme: Boolean) = copy(useDarkTheme = useDarkTheme)
}
