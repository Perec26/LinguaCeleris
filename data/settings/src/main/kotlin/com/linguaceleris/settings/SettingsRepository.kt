package com.linguaceleris.settings

import com.linguaceleris.services.preferences.DataStoreService
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class SettingsRepository @Inject constructor(private val dataStoreService: DataStoreService) {

    fun getUseSystemThemeFlow() = dataStoreService.getUseSystemThemeFlow()
    fun getUseDarkThemeFlow() = dataStoreService.getUseDarkThemeFlow()

    suspend fun getUseSystemTheme() = getUseSystemThemeFlow().first()
    suspend fun getUseDarkTheme() = getUseDarkThemeFlow().first()

    suspend fun updateUseSystemTheme(useSystemTheme: Boolean) =
        dataStoreService.updateUseSystemTheme(useSystemTheme)

    suspend fun updateUseDarkTheme(useDarkTheme: Boolean) =
        dataStoreService.updateUseDarkTheme(useDarkTheme)
}
