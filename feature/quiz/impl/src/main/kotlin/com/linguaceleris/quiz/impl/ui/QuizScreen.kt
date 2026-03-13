package com.linguaceleris.quiz.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme
import com.linguaceleris.designsystem.widgets.ScreenPreviews
import com.linguaceleris.quiz.impl.ui.model.TaskUI
import com.linguaceleris.quiz.impl.ui.widgets.MatchWidget
import com.linguaceleris.quiz.impl.ui.widgets.SelectCorrectWidget

@Composable
internal fun QuizScreen(viewModel: QuizViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    QuizScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun QuizScreenContent(state: QuizUiState, onEvent: (QuizEvent) -> Unit) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            when (state.screenState) {
                ScreenState.LOADING -> CircularProgressIndicator()

                ScreenState.CONTENT -> Quiz(state, onEvent)

                // TODO: вынести в общие ресурсы после добавления обработки ошибкок
                ScreenState.ERROR -> Text(text = "Что - то пошло не так")
            }
        }
    }
}

@Composable
private fun Quiz(state: QuizUiState, onEvent: (QuizEvent) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            progress = { state.progressFloat },
        )
        when (val currentTask = state.currentTask) {
            is TaskUI.SelectCorrectAnswer -> {
                SelectCorrectWidget(
                    modifier = Modifier.weight(0.5f),
                    task = currentTask,
                    onCheckButtonClick = { onEvent(QuizEvent.OnCheckButtonClick) },
                    onContinueButtonClick = { onEvent(QuizEvent.OnContinueButtonClick) },
                    onVariantSelected = { variant -> onEvent(QuizEvent.SelectAnswer(variant)) },
                )
            }

            is TaskUI.Matching -> {
                MatchWidget(
                    task = currentTask,
                    onContinueButtonClick = { onEvent(QuizEvent.OnContinueButtonClick) },
                    onAudioClick = { onEvent(QuizEvent.OnAudioClick(it)) },
                    onVariantSelected = { variant -> onEvent(QuizEvent.SelectAnswer(variant)) },
                )
            }

            null -> {}
        }
    }
}

@ScreenPreviews
@Composable
private fun QuizScreenLoadingPreview() {
    LinguaCelerisTheme {
        QuizScreenContent(
            state = quizStateMock.copy(screenState = ScreenState.LOADING),
        ) {}
    }
}

@ScreenPreviews
@Composable
private fun QuizScreenContentPreview() {
    LinguaCelerisTheme {
        QuizScreenContent(
            state = quizStateMock,
        ) {}
    }
}
