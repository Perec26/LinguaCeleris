package com.linguaceleris.quizselection.impl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme
import com.linguaceleris.designsystem.widgets.LCFilledButton
import com.linguaceleris.designsystem.widgets.ScreenPreviews
import com.linguaceleris.quizselection.impl.R
import com.linguaceleris.quizselection.impl.ui.model.DayQuizzesUI

@Composable
internal fun QuizSelectionScreen(viewModel: QuizSelectionViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    QuizSelectionScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun QuizSelectionScreenContent(
    state: QuizSelectionUiState,
    onEvent: (QuizSelectionEvent) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            if (state.dayQuizzes.basic != null) {
                QuizButton(
                    text = stringResource(R.string.quiz_selection_basic),
                    onClick = { onEvent(QuizSelectionEvent.OnQuizClick(state.dayQuizzes.basic)) },
                )
            }
            if (state.dayQuizzes.intermediate != null) {
                QuizButton(
                    text = stringResource(R.string.quiz_selection_intermediate),
                    onClick = {
                        onEvent(QuizSelectionEvent.OnQuizClick(state.dayQuizzes.intermediate))
                    },
                )
            }
            if (state.dayQuizzes.advanced != null) {
                QuizButton(
                    text = stringResource(R.string.quiz_selection_advanced),
                    onClick = {
                        onEvent(QuizSelectionEvent.OnQuizClick(state.dayQuizzes.advanced))
                    },
                )
            }
            LCFilledButton(
                text = "Выйти",
                onClick = { onEvent(QuizSelectionEvent.OnSignOut) },
            )
        }
    }
}

@Composable
private fun QuizButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = onClick,
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = text,
        )
    }
}

@ScreenPreviews
@Composable
private fun QuizSelectionScreenLoadingPreview() {
    LinguaCelerisTheme {
        QuizSelectionScreenContent(QuizSelectionUiState()) {}
    }
}

@ScreenPreviews
@Composable
private fun QuizSelectionScreenContentPreview() {
    LinguaCelerisTheme {
        QuizSelectionScreenContent(
            state = QuizSelectionUiState(
                isLoading = false,
                dayQuizzes = DayQuizzesUI("", "", ""),
            ),
        ) {}
    }
}
