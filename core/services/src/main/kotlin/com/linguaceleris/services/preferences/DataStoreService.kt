package com.linguaceleris.services.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import javax.inject.Inject
import kotlinx.coroutines.flow.map

class DataStoreService @Inject constructor(private val dataStore: DataStore<Preferences>) {

    fun getUseSystemThemeFlow() = dataStore.data.map { it[USE_SYSTEM_THEME] ?: true }
    fun getUseDarkThemeFlow() = dataStore.data.map { it[USE_DARK_THEME] ?: true }

    suspend fun updateUseSystemTheme(useSystemTheme: Boolean) {
        dataStore.edit { it[USE_SYSTEM_THEME] = useSystemTheme }
    }

    suspend fun updateUseDarkTheme(useDarkTheme: Boolean) {
        dataStore.edit { it[USE_DARK_THEME] = useDarkTheme }
    }

    companion object {
        val USE_SYSTEM_THEME = booleanPreferencesKey("use_system_theme")
        val USE_DARK_THEME = booleanPreferencesKey("use_dark_theme")
    }
}
