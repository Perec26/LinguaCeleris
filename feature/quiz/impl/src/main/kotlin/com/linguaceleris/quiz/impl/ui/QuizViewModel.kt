package com.linguaceleris.quiz.impl.ui

import com.linguaceleris.media.PlayerManager
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quiz.impl.R
import com.linguaceleris.quiz.impl.domain.GetTasksUseCase
import com.linguaceleris.quiz.impl.ui.model.TaskUI
import com.linguaceleris.quiz.impl.ui.model.WordCardUI
import com.linguaceleris.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class QuizViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getTasksUseCase: GetTasksUseCase,
    private val playerManager: PlayerManager,
) : BaseViewModel<QuizUiState, QuizEvent>(initialState = QuizUiState()) {

    init {
        launch {
            val tasks = getTasksUseCase.invoke()
            updateState { onTaskLoaded(tasks) }
        }
    }

    override fun onEvent(event: QuizEvent) {
        when (event) {
            QuizEvent.OnCheckButtonClick -> onCheckClicked()
            is QuizEvent.SelectAnswer -> onSelectAnswer(event.variant)
            QuizEvent.OnContinueButtonClick -> onContinueButtonClick()
            is QuizEvent.OnAudioClick -> event.audio?.let(::onAudioClick)
        }
    }

    private fun onSelectAnswer(event: WordCardUI) {
        updateState { onCardClicked(event) }
        val currentTask = currentState.currentTask
        if (currentTask is TaskUI.Matching) {
            if (currentTask.hasError) playError()
            if (currentTask.isDone) playCorrect()
        }
    }

    private fun onContinueButtonClick() {
        playerManager.stop()

        if (currentState.lastTask) {
            navigator.back()
        } else {
            updateState { showNextTask() }
        }
    }

    private fun onCheckClicked() {
        val currentTask = currentState.currentTask
        if (currentTask is TaskUI.SelectCorrectAnswer) {
            playerManager.stop()
            checkSelectCorrectAnswer(currentTask)
        }
        updateState { onCheckClicked() }
    }

    private fun playCorrect() = playerManager.playRaw(R.raw.quiz_correct)

    private fun playError() = playerManager.playRaw(R.raw.quiz_error)

    private fun checkSelectCorrectAnswer(currentTask: TaskUI.SelectCorrectAnswer) {
        if (currentTask.isCorrect) playCorrect() else playError()
    }

    private fun onAudioClick(audio: String) {
        playerManager.playUrl(audio)
    }

    override fun onCleared() {
        super.onCleared()
        playerManager.release()
    }
}
