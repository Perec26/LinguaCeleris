package com.linguaceleris.start.impl

import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quiz.api.QuizNavKey
import com.linguaceleris.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class StartViewModel @Inject constructor(
    val navigator: Navigator,
) : BaseViewModel<StartUiState, StartEvent>(initialState = StartUiState()) {

    override fun onEvent(event: StartEvent) {
        when (event) {
            StartEvent.OnButtonClick -> navigator.navigateTo(QuizNavKey)
            StartEvent.OnButton2Click -> updateState { increaseNumber() }
        }
    }
}
