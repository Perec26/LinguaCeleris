package com.linguaceleris.home.impl.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.theme.buttonColors
import com.linguaceleris.designsystem.theme.extendedColors
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.buttons.ButtonSize
import com.linguaceleris.designsystem.widgets.buttons.LCFilledButton
import com.linguaceleris.home.impl.R
import com.linguaceleris.home.impl.ui.HomeEvent
import com.linguaceleris.home.impl.ui.model.QuizCompletionUI

@Composable
internal fun QuizSelectionWidget(completion: QuizCompletionUI, onEvent: (HomeEvent) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
    ) {
        LCFilledButton(
            modifier = Modifier.fillMaxWidth(),
            colors = MaterialTheme.extendedColors.green.buttonColors(),
            text = stringResource(R.string.home_quiz_level_basic),
            icon = painterResource(R.drawable.home_check_circle).takeIf {
                completion.basicIsCompleted
            },
            buttonSize = ButtonSize.LARGE,
            onClick = { onEvent(HomeEvent.OnBasicQuizClick) },
        )

        LCFilledButton(
            modifier = Modifier.fillMaxWidth(),
            colors = MaterialTheme.extendedColors.yellow.buttonColors(),
            icon = painterResource(R.drawable.home_check_circle).takeIf {
                completion.intermediateIsCompleted
            },
            text = stringResource(R.string.home_quiz_level_intermediate),
            buttonSize = ButtonSize.LARGE,
            onClick = { onEvent(HomeEvent.OnIntermediateQuizClick) },
        )

        LCFilledButton(
            modifier = Modifier.fillMaxWidth(),
            colors = MaterialTheme.extendedColors.red.buttonColors(),
            icon = painterResource(R.drawable.home_check_circle).takeIf {
                completion.advancedIsCompleted
            },
            text = stringResource(R.string.home_quiz_level_advanced),
            buttonSize = ButtonSize.LARGE,
            onClick = { onEvent(HomeEvent.OnAdvanceQuizClick) },
        )
    }
}

@PreviewLightDark
@Composable
private fun QuizSelectionWidgetPreview() {
    LCPreview {
        QuizSelectionWidget(completion = QuizCompletionUI()) {}
    }
}

@PreviewLightDark
@Composable
private fun QuizSelectionWidgetAllDonePreview() {
    LCPreview {
        QuizSelectionWidget(
            completion = QuizCompletionUI(
                basicIsCompleted = true,
                intermediateIsCompleted = true,
                advancedIsCompleted = true,
            ),
        ) {}
    }
}
