package com.linguaceleris.quiz.impl.ui.summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.LoadingScaffold
import com.linguaceleris.designsystem.widgets.ScreenPreviews
import com.linguaceleris.designsystem.widgets.buttons.LCFilledButton
import com.linguaceleris.quiz.impl.R

@Composable
internal fun QuizSummaryScreen(viewModel: QuizSummaryViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    QuizSummaryScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun QuizSummaryScreenContent(
    state: QuizSummaryUiState,
    onEvent: (QuizSummaryEvent) -> Unit,
) {
    LoadingScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val textRes = if (state.isSuccessful) {
                R.string.quiz_summary_success_text
            } else {
                R.string.quiz_summary_failure_text
            }

            Text(
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                text = stringResource(textRes),
            )

            LCFilledButton(
                text = stringResource(R.string.quiz_summary_try_again),
                onClick = { onEvent(QuizSummaryEvent.OnTryAgainClicked) },
            )
        }
    }
}

@ScreenPreviews
@Composable
private fun QuizSummaryScreenPreview() {
    LCPreview {
        QuizSummaryScreenContent(QuizSummaryUiState()) {}
    }
}
