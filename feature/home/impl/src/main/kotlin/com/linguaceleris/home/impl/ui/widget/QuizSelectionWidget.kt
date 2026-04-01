package com.linguaceleris.home.impl.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.theme.buttonColors
import com.linguaceleris.designsystem.theme.extendedColors
import com.linguaceleris.designsystem.widgets.ButtonSize
import com.linguaceleris.designsystem.widgets.LCFilledButton
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.home.impl.R
import com.linguaceleris.home.impl.ui.HomeEvent
import com.linguaceleris.home.impl.ui.model.DayQuizzesUI
import com.linguaceleris.home.impl.ui.quizzesUIMock

@Composable
internal fun QuizSelectionWidget(
    quizzesUI: DayQuizzesUI,
    isLoading: Boolean,
    onEvent: (HomeEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            quizzesUI.basic?.let {
                LCFilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    colors = MaterialTheme.extendedColors.green.buttonColors(),
                    text = stringResource(R.string.home_quiz_level_easy),
                    buttonSize = ButtonSize.EXTRA_LARGE,
                    onClick = { onEvent(HomeEvent.OnQuizClick(it)) },
                )
            }

            quizzesUI.intermediate?.let {
                LCFilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    colors = MaterialTheme.extendedColors.yellow.buttonColors(),

                    text = stringResource(R.string.home_quiz_level_normal),
                    buttonSize = ButtonSize.EXTRA_LARGE,
                    onClick = { onEvent(HomeEvent.OnQuizClick(it)) },
                )
            }

            quizzesUI.advanced?.let {
                LCFilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    colors = MaterialTheme.extendedColors.red.buttonColors(),
                    text = stringResource(R.string.home_quiz_level_hard),
                    buttonSize = ButtonSize.EXTRA_LARGE,
                    onClick = { onEvent(HomeEvent.OnQuizClick(it)) },
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun QuizSelectionWidgetPreview() {
    LCPreview {
        QuizSelectionWidget(
            quizzesUI = quizzesUIMock,
            isLoading = false,
        ) {}
    }
}
