package com.linguaceleris.ui

import com.linguaceleris.navigation.Navigator
import com.linguaceleris.start.api.StartNavKey
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val navigator: Navigator,
) : BaseViewModel<MainState, MainEvent>(initialState = MainState) {

    init {
        navigator.startWith(StartNavKey)
    }

    override fun onEvent(event: MainEvent) {
    }
}
