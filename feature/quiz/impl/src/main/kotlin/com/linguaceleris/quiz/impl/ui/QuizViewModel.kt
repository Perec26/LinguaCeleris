package com.linguaceleris.quiz.impl.ui

import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quiz.impl.domain.GetTasksUseCase
import com.linguaceleris.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class QuizViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getTasksUseCase: GetTasksUseCase,
) : BaseViewModel<QuizUiState, QuizEvent>(initialState = QuizUiState()) {

    init {
        launch {
            val tasks = getTasksUseCase.invoke()
            updateState { onTaskLoaded(tasks) }
        }
    }

    override fun onEvent(event: QuizEvent) {
        when (event) {
            QuizEvent.OnCheckButtonClick -> updateState { onCheckClicked() }
            is QuizEvent.SelectAnswer -> updateState { onCardClicked(event.variant) }
            QuizEvent.OnContinueButtonClick -> onContinueButtonClick()
        }
    }

    private fun onContinueButtonClick() {
        if (currentState.lastTask) {
            navigator.back()
        } else {
            updateState { showNextTask() }
        }
    }
}
