package com.linguaceleris.ui

import com.linguaceleris.domain.GetUseDarkThemeFlowUseCase
import com.linguaceleris.domain.GetUseSystemThemeFlowUseCase
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.start.api.StartNavKey
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getUseSystemThemeUseCase: GetUseSystemThemeFlowUseCase,
    private val getUseDarkThemeUseCase: GetUseDarkThemeFlowUseCase,
) : BaseViewModel<MainState, MainEvent>(initialState = MainState()) {

    init {
        navigator.startWith(StartNavKey)
        subscribeThemeChange()
    }

    override fun onEvent(event: MainEvent) {}

    private fun subscribeThemeChange() {
        launch {
            getUseSystemThemeUseCase().collect {
                updateState { updateUseSystemTheme(it) }
            }
        }
        launch {
            getUseDarkThemeUseCase().collect {
                updateState { updateUseDarkTheme(it) }
            }
        }
    }
}
