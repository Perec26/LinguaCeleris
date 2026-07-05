@file:PendingUiTests

package com.linguaceleris.quiz.impl.ui.summary

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.designsystem.theme.buttonColors
import com.linguaceleris.designsystem.widgets.CommonErrorWidget
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.LoadingScaffold
import com.linguaceleris.designsystem.widgets.ScreenPreviews
import com.linguaceleris.designsystem.widgets.SpacerHeight
import com.linguaceleris.designsystem.widgets.buttons.ButtonSize
import com.linguaceleris.designsystem.widgets.buttons.LCFilledButton
import com.linguaceleris.designsystem.widgets.buttons.LCTextButton
import com.linguaceleris.quiz.api.QuizLevel
import com.linguaceleris.quiz.impl.R
import com.linguaceleris.quiz.impl.ui.summary.model.buttonColors
import com.linguaceleris.quiz.impl.ui.summary.model.title
import com.linguaceleris.testing.PendingUiTests
import com.linguaceleris.ui.ScreenState

@Composable
internal fun QuizSummaryScreen(viewModel: QuizSummaryViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    QuizSummaryScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun QuizSummaryScreenContent(
    state: QuizSummaryUiState,
    onEvent: (QuizSummaryEvent) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when (state.screenState) {
            ScreenState.LOADING -> CircularProgressIndicator()
            ScreenState.ERROR -> CommonErrorWidget { onEvent(QuizSummaryEvent.OnReloadData) }
            ScreenState.CONTENT -> QuizSummaryContent(state, onEvent)
        }
    }
}

@Composable
private fun QuizSummaryContent(state: QuizSummaryUiState, onEvent: (QuizSummaryEvent) -> Unit) {
    LoadingScaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    modifier = Modifier
                        .sizeIn(maxWidth = 240.dp, maxHeight = 240.dp)
                        .fillMaxWidth()
                        .weight(1f),
                    painter = painterResource(state.result.icon),
                    contentDescription = null,
                )

                SpacerHeight(40.dp)

                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(state.result.title),
                    style = MaterialTheme.typography.displayMedium,
                )

                Text(
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center,
                    text = stringResource(state.result.description),
                    style = MaterialTheme.typography.headlineSmall,
                )
            }

            if (state.isSuccessful) {
                val completionText = if (state.hasUnfinishedQuizzes) {
                    R.string.quiz_summary_not_finished_task
                } else {
                    R.string.quiz_summary_all_complete
                }
                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    text = stringResource(completionText),
                )

                if (state.hasUnfinishedQuizzes) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        state.unfinishedQuizzes.forEach {
                            LCFilledButton(
                                modifier = Modifier.weight(1f),
                                text = stringResource(it.title),
                                colors = it.buttonColors(),
                                buttonSize = ButtonSize.MEDIUM,
                                onClick = { onEvent(QuizSummaryEvent.OnNextQuizClick(it)) },
                            )
                        }
                    }
                }
            } else {
                LCFilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.quiz_summary_try_again),
                    colors = MaterialTheme.colorScheme.tertiaryContainer.buttonColors(),
                    buttonSize = ButtonSize.MEDIUM,
                    onClick = { onEvent(QuizSummaryEvent.OnTryAgainClicked) },
                )
            }
            LCTextButton(
                modifier = Modifier.fillMaxWidth(),
                buttonSize = ButtonSize.MEDIUM,
                text = stringResource(R.string.quiz_summary_return),
                onClick = { onEvent(QuizSummaryEvent.OnBackClick) },
            )
        }
    }
}

@ScreenPreviews
@Composable
private fun QuizSummaryScreenSuccessPreview() {
    LCPreview {
        QuizSummaryScreenContent(
            QuizSummaryUiState(screenState = ScreenState.CONTENT),
        ) {}
    }
}

@ScreenPreviews
@Composable
private fun QuizSummaryScreenSuccessHasUnfinishedPreview() {
    LCPreview {
        QuizSummaryScreenContent(
            QuizSummaryUiState(
                unfinishedQuizzes = listOf(
                    QuizLevel.INTERMEDIATE,
                    QuizLevel.ADVANCED,
                ),
                screenState = ScreenState.CONTENT,
            ),
        ) {}
    }
}

@Preview(device = "spec:width=720px,height=1000px,dpi=320")
@ScreenPreviews
@Composable
private fun QuizSummaryScreeFailurePreview() {
    LCPreview {
        QuizSummaryScreenContent(
            QuizSummaryUiState(
                result = QuizResult.FAILURE,
                screenState = ScreenState.CONTENT,
            ),
        ) {}
    }
}

@ScreenPreviews
@Composable
private fun QuizSummaryScreeLoading() {
    LCPreview {
        QuizSummaryScreenContent(QuizSummaryUiState(screenState = ScreenState.LOADING)) {}
    }
}

@ScreenPreviews
@Composable
private fun QuizSummaryScreeError() {
    LCPreview {
        QuizSummaryScreenContent(QuizSummaryUiState(screenState = ScreenState.ERROR)) {}
    }
}
