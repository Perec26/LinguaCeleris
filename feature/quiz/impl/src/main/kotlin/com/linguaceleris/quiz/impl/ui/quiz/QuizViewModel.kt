package com.linguaceleris.quiz.impl.ui.quiz

import com.linguaceleris.media.PlayerManager
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quiz.api.QuizDifficulty
import com.linguaceleris.quiz.impl.R
import com.linguaceleris.quiz.impl.domain.GetTasksUseCase
import com.linguaceleris.quiz.impl.domain.SelectVariantUseCase
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
    private val selectVariantUseCase: SelectVariantUseCase,
    @Assisted val quizDifficulty: QuizDifficulty,
) : BaseViewModel<QuizUiState, QuizEvent>(initialState = QuizUiState()) {

    init {
        loadTasks()
    }

    override fun onEvent(event: QuizEvent) {
        when (event) {
            is QuizEvent.OnAudioClick -> event.audio?.let(::playAudio)
            is QuizEvent.SelectAnswer -> onSelectVariant(event.variant)
            QuizEvent.OnBackClick -> updateState { showExitDialog() }
            QuizEvent.OnCheckButtonClick -> onCheckClicked()
            QuizEvent.OnContinueButtonClick -> onContinueButtonClick()
            QuizEvent.OnReloadClick -> loadTasks()
            QuizEvent.OnExitCancelClick -> updateState { hideExitDialog() }
            QuizEvent.OnExitConfirmClick -> exit()
        }
    }

    private fun loadTasks() {
        updateState { onLoading() }

        launch(
            onError = { updateState { onError() } },
        ) {
            val tasks = getTasksUseCase(quizDifficulty)
            updateState { onTaskLoaded(tasks) }
        }
    }

    private fun onSelectVariant(variant: WordCardUI) {
        val updatedTask = currentState.currentTask?.let {
            selectVariantUseCase(it, variant)
        } ?: return

        updateState { onVariantSelected(updatedTask) }
        if (updatedTask is TaskUI.Matching) {
            if (updatedTask.hasError) playError()
            if (updatedTask.isDone) playCorrect()
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
        playerManager.stop()
        val currentTask = currentState.currentTask as? TaskUI.SelectCorrectAnswer ?: return
        if (currentTask.isCorrect) playCorrect() else playError()
        updateState { onTaskChecked(currentTask.copy(isChecked = true)) }
    }

    private fun playCorrect() = playerManager.playRaw(R.raw.quiz_correct)

    private fun playError() = playerManager.playRaw(R.raw.quiz_error)

    private fun playAudio(audio: String) = playerManager.playUrl(audio)

    private fun exit() {
        updateState { hideExitDialog() }
        navigator.back()
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
