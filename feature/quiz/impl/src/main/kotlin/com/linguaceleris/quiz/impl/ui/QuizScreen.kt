package com.linguaceleris.quiz.impl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme
import com.linguaceleris.designsystem.widgets.ScreenPreviews
import com.linguaceleris.quiz.impl.ui.model.TaskUI
import com.linguaceleris.quiz.impl.ui.widgets.LivesIndicator
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
        TopPanel(
            progress = state.progressFloat,
            lives = state.lives,
            onBackClick = { onEvent(QuizEvent.OnBackClick) },
        )
        when (val currentTask = state.currentTask) {
            is TaskUI.SelectCorrectAnswer -> {
                SelectCorrectWidget(
                    modifier = Modifier.weight(0.5f),
                    task = currentTask,
                    onAudioClick = { onEvent(QuizEvent.OnAudioClick(it)) },
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

@Composable
private fun TopPanel(progress: Float, lives: Int, onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onBackClick,
        ) {
            Icon(
                painter = painterResource(com.linguaceleris.designsystem.R.drawable.arrow_back),
                contentDescription = null,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .height(16.dp)
                    .weight(1f),
                progress = { progress },
            )

            LivesIndicator(remainingLives = lives)
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
