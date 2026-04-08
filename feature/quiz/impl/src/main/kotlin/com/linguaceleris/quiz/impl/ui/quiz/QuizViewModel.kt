package com.linguaceleris.quiz.impl.ui.quiz

import com.linguaceleris.media.PlayerManager
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quiz.api.QuizDifficulty
import com.linguaceleris.quiz.impl.R
import com.linguaceleris.quiz.impl.domain.GetTasksUseCase
import com.linguaceleris.quiz.impl.navigation.navigateToSummary
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI
import com.linguaceleris.quiz.impl.ui.quiz.model.WordCardUI
import com.linguaceleris.ui.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = QuizViewModel.Factory::class)
internal class QuizViewModel @AssistedInject constructor(
    private val navigator: Navigator,
    private val getTasksUseCase: GetTasksUseCase,
    private val playerManager: PlayerManager,
    @Assisted val quizDifficulty: QuizDifficulty,
) : BaseViewModel<QuizUiState, QuizEvent>(initialState = QuizUiState()) {

    init {
        launch {
            val tasks = getTasksUseCase(quizDifficulty)
            updateState { onTaskLoaded(tasks) }
        }
    }

    override fun onEvent(event: QuizEvent) {
        when (event) {
            is QuizEvent.OnAudioClick -> event.audio?.let(::onAudioClick)
            is QuizEvent.SelectAnswer -> onSelectAnswer(event.variant)
            QuizEvent.OnBackClick -> navigator.back()
            QuizEvent.OnCheckButtonClick -> onCheckClicked()
            QuizEvent.OnContinueButtonClick -> onContinueButtonClick()
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

        if (currentState.lives <= 0) {
            navigator.navigateToSummary(quizDifficulty, false)
            return
        }
        if (currentState.lastTask) {
            navigator.navigateToSummary(quizDifficulty, true)
            return
        }

        updateState { showNextTask() }
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

    @AssistedFactory
    interface Factory {
        fun create(quizDifficulty: QuizDifficulty): QuizViewModel
    }
}
