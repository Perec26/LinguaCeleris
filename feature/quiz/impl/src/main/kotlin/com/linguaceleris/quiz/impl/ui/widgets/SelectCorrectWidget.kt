package com.linguaceleris.quiz.impl.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme
import com.linguaceleris.designsystem.widgets.CardState
import com.linguaceleris.designsystem.widgets.DefaultFilledButton
import com.linguaceleris.designsystem.widgets.LCCardWithText
import com.linguaceleris.quiz.impl.R
import com.linguaceleris.quiz.impl.ui.imageSelectWordTranslation
import com.linguaceleris.quiz.impl.ui.model.TaskUI
import com.linguaceleris.quiz.impl.ui.model.WordCardUI
import com.linguaceleris.quiz.impl.ui.selectCorrectAnswerMock

@Composable
internal fun SelectCorrectWidget(
    modifier: Modifier = Modifier,
    task: TaskUI.SelectCorrectAnswer,
    onVariantSelected: (WordCardUI) -> Unit = {},
    onAudioClick: (String?) -> Unit = {},
    onCheckButtonClick: () -> Unit = {},
    onContinueButtonClick: () -> Unit = {},
) {
    val haptic = LocalHapticFeedback.current
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(task.type.text),
        )

        TaskContentWidget(task = task, onAudioClick = onAudioClick)

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                task.answerVariants.forEach { variant ->
                    val isSelected = variant == task.selectedVariant
                    val isCorrect = variant == task.correctAnswer

                    val state = when {
                        task.isChecked && isCorrect -> CardState.RIGHT
                        task.isChecked && isSelected -> CardState.WRONG
                        isSelected -> CardState.SELECTED
                        else -> CardState.DEFAULT
                    }
                    LCCardWithText(
                        state = state,
                        enabled = !task.isChecked || isSelected,
                        text = variant.text,
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                            onVariantSelected(variant)
                        },
                    )
                }
            }
        }

        if (!task.isChecked) {
            DefaultFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textModifier = Modifier.padding(8.dp),
                text = stringResource(R.string.quiz_check),
                isEnable = task.selectedVariant != null,
                onClick = onCheckButtonClick,
            )
        }

        if (task.isChecked) {
            DefaultFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textModifier = Modifier.padding(8.dp),
                text = stringResource(R.string.quiz_continue),
                onClick = onContinueButtonClick,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SelectCorrectWidgetPreview() {
    LinguaCelerisTheme {
        Surface {
            SelectCorrectWidget(
                task = selectCorrectAnswerMock,
            ) {}
        }
    }
}

@PreviewLightDark
@Composable
private fun SelectCorrectWidgetImagePreview() {
    LinguaCelerisTheme {
        Surface {
            SelectCorrectWidget(
                task = imageSelectWordTranslation,
            ) {}
        }
    }
}
