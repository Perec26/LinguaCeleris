package com.linguaceleris.ui

import androidx.lifecycle.ViewModel

class MainViewModel: BaseViewModel<MainState, MainEvent, MainNavigationEvent>(
    initialState = MainState
) {
    override fun onEvent(event: MainEvent) {

    }
}