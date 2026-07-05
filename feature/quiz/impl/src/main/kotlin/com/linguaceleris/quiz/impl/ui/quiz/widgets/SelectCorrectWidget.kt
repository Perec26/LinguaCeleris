@file:PendingUiTests

package com.linguaceleris.quiz.impl.ui.quiz.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.buttons.ButtonSize
import com.linguaceleris.designsystem.widgets.buttons.LCFilledButton
import com.linguaceleris.quiz.impl.R
import com.linguaceleris.quiz.impl.ui.quiz.imageSelectWordTranslation
import com.linguaceleris.quiz.impl.ui.quiz.listenMock
import com.linguaceleris.quiz.impl.ui.quiz.model.AnswerType
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI
import com.linguaceleris.quiz.impl.ui.quiz.model.WordCardUI
import com.linguaceleris.quiz.impl.ui.quiz.selectAudioMock
import com.linguaceleris.quiz.impl.ui.quiz.selectCorrectAnswerMock
import com.linguaceleris.testing.PendingUiTests

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
            maxLines = 2,
            textAlign = TextAlign.Center,
            autoSize = TextAutoSize.StepBased(
                maxFontSize = MaterialTheme.typography.headlineSmall.fontSize,
            ),
        )

        TaskContentWidget(
            modifier = Modifier.weight(1f),
            task = task,
            onAudioClick = onAudioClick,
        )

        BoxWithConstraints(
            contentAlignment = Alignment.Center,
        ) {
            val columnAmount = if (maxHeight < 600.dp) 2 else 1
            LazyVerticalGrid(
                columns = GridCells.Fixed(columnAmount),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(task.answerVariants) { variant ->
                    val isSelected = variant == task.selectedVariant
                    val isCorrect = variant == task.correctAnswer

                    val state = when {
                        task.isChecked && isCorrect -> CardState.RIGHT
                        task.isChecked && isSelected -> CardState.WRONG
                        isSelected -> CardState.SELECTED
                        else -> CardState.DEFAULT
                    }
                    val isAudio = task.type.answerType == AnswerType.AUDIO
                    AnswerCard(
                        state = state,
                        enabled = !task.isChecked || isSelected,
                        text = variant.text,
                        isAudio = isAudio,
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                            if (isAudio) onAudioClick(variant.audio)
                            onVariantSelected(variant)
                        },
                    )
                }
            }
        }

        val (text, oBottomButtonClick) = if (task.isChecked) {
            R.string.quiz_continue to onContinueButtonClick
        } else {
            R.string.quiz_check to onCheckButtonClick
        }

        LCFilledButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            buttonSize = ButtonSize.MEDIUM,
            isEnable = task.selectedVariant != null,
            text = stringResource(text),
            onClick = oBottomButtonClick,
        )
    }
}

@PreviewLightDark
@Composable
private fun SelectCorrectWidgetPreview() {
    LCPreview {
        SelectCorrectWidget(task = selectCorrectAnswerMock) {}
    }
}

@PreviewLightDark
@Composable
private fun SelectCorrectWidgetListen() {
    LCPreview {
        SelectCorrectWidget(
            task = listenMock,
        ) {}
    }
}

@PreviewLightDark
@Composable
private fun SelectCorrectWidgetAudio() {
    LCPreview {
        SelectCorrectWidget(
            task = selectAudioMock,
        ) {}
    }
}

@PreviewLightDark
@Composable
private fun SelectCorrectWidgetImagePreview() {
    LCPreview {
        SelectCorrectWidget(
            task = imageSelectWordTranslation,
        ) {}
    }
}

@Preview(device = "id:Galaxy Nexus")
@PreviewLightDark
@Composable
private fun SelectCorrectWidgetImagePreview2() {
    LCPreview {
        SelectCorrectWidget(
            task = imageSelectWordTranslation,
        ) {}
    }
}
