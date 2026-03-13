package com.linguaceleris.quiz.impl.ui

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quiz.impl.domain.GetTasksUseCase
import com.linguaceleris.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class QuizViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getTasksUseCase: GetTasksUseCase,
    private val player: ExoPlayer,
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
            is QuizEvent.OnAudioClick -> event.audio?.let(::onAudioClick)
        }
    }

    private fun onContinueButtonClick() {
        if (currentState.lastTask) {
            navigator.back()
        } else {
            updateState { showNextTask() }
        }
    }

    private fun onAudioClick(audio: String) {
        val mediaItem = MediaItem.fromUri(audio)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}
