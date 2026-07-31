package com.linguaceleris.settings.impl.ui

import com.linguaceleris.config.AppConfig
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.settings.impl.domain.GetUseDarkThemeUseCase
import com.linguaceleris.settings.impl.domain.GetUseSystemThemeUseCase
import com.linguaceleris.settings.impl.domain.UpdateUseDarkThemeUseCase
import com.linguaceleris.settings.impl.domain.UpdateUseSystemThemeUseCase
import com.linguaceleris.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getUseSystemThemeUseCase: GetUseSystemThemeUseCase,
    private val getUseDarkThemeUseCase: GetUseDarkThemeUseCase,
    private val updateUseSystemThemeUseCase: UpdateUseSystemThemeUseCase,
    private val updateUseDarkThemeUseCase: UpdateUseDarkThemeUseCase,
    private val config: AppConfig,
) : BaseViewModel<SettingsUiState, SettingsEvent>(initialState = SettingsUiState()) {

    init {
        getSettingsValues()
    }

    override fun onEvent(event: SettingsEvent) {
        when (event) {
            SettingsEvent.OnBackPressed -> navigator.back()
            SettingsEvent.OnUseDarkThemeClick -> onUseDarkThemeClick()
            SettingsEvent.OnUseSystemThemeClick -> onUseSystemThemeClick()
        }
    }

    private fun getSettingsValues() {
        launch {
            val useSystemTheme = getUseSystemThemeUseCase()
            val useDarkTheme = getUseDarkThemeUseCase()
            val version = config.version
            updateState { settingsLoaded(useSystemTheme, useDarkTheme, version) }
        }
    }

    private fun onUseSystemThemeClick() {
        launch {
            val useSystemTheme = !currentState.useSystemTheme
            updateUseSystemThemeUseCase(useSystemTheme)
            updateState { updateUseSystemTheme(useSystemTheme) }
        }
    }

    private fun onUseDarkThemeClick() {
        launch {
            val useDarkTheme = !currentState.useDarkTheme
            updateUseDarkThemeUseCase(useDarkTheme)
            updateState { updateUseDarkTheme(useDarkTheme) }
        }
    }
}
