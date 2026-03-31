package com.linguaceleris.home.impl.ui

import com.linguaceleris.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor() :
    BaseViewModel<HomeUiState, HomeEvent>(initialState = HomeUiState()) {

    override fun onEvent(event: HomeEvent) {
        when (event) {
            else -> {} // TODO: реализовать ивенты
        }
    }
}
