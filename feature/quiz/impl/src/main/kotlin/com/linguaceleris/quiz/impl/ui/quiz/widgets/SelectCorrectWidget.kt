@file:PendingUiTests

package com.linguaceleris.quiz.impl.ui.quiz.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.SmallScreenPreview
import com.linguaceleris.designsystem.widgets.buttons.ButtonSize
import com.linguaceleris.designsystem.widgets.buttons.LCFilledButton
import com.linguaceleris.quiz.impl.R
import com.linguaceleris.quiz.impl.ui.quiz.imageSelectWordTranslation
import com.linguaceleris.quiz.impl.ui.quiz.listenMock
import com.linguaceleris.quiz.impl.ui.quiz.model.AnswerType
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI
import com.linguaceleris.quiz.impl.ui.quiz.model.WordCardUI
import com.linguaceleris.quiz.impl.ui.quiz.selectAudioMock
import com.linguaceleris.quiz.impl.ui.quiz.selectCorrectAnswerFillInBlankMock
import com.linguaceleris.quiz.impl.ui.quiz.selectCorrectAnswerInThreeMock
import com.linguaceleris.quiz.impl.ui.quiz.selectCorrectAnswerMock
import com.linguaceleris.testing.PendingUiTests

@Composable
internal fun SelectCorrectWidget(
    modifier: Modifier = Modifier,
    task: TaskUI.SelectCorrectAnswer,
    onVariantSelected: (WordCardUI) -> Unit = {},
    isBigScreen: Boolean = true,
    onAudioClick: (String?) -> Unit = {},
    onCheckButtonClick: () -> Unit = {},
    onContinueButtonClick: () -> Unit = {},
) {
    val haptic = LocalHapticFeedback.current

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        val textColor = MaterialTheme.colorScheme.onSurface
        BasicText(
            modifier = Modifier.padding(16.dp),
            text = stringResource(task.type.text),
            maxLines = 2,
            style = TextStyle(
                textAlign = TextAlign.Center,
            ),
            color = { textColor },
            autoSize = TextAutoSize.StepBased(
                minFontSize = 8.sp,
                maxFontSize = MaterialTheme.typography.headlineSmall.fontSize,
            ),
        )

        TaskContentWidget(
            modifier = Modifier.weight(1f, fill = false),
            task = task,
            onAudioClick = onAudioClick,
        )

        Column {
            val columnAmount = if (isBigScreen || task.answerVariants.size < 4) 1 else 2
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
                        bigSize = isBigScreen,
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                            if (isAudio) onAudioClick(variant.audio)
                            onVariantSelected(variant)
                        },
                    )
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

@PreviewLightDark
@Composable
private fun SelectCorrectInThreeWidgetImagePreview() {
    LCPreview {
        SelectCorrectWidget(
            task = selectCorrectAnswerInThreeMock,
        ) {}
    }
}

@SmallScreenPreview
@Composable
private fun SelectCorrectWidgetSmallPreview() {
    LCPreview {
        SelectCorrectWidget(isBigScreen = false, task = selectCorrectAnswerMock) {}
    }
}

@SmallScreenPreview
@Composable
private fun SelectCorrectWidgetSmallListen() {
    LCPreview {
        SelectCorrectWidget(
            isBigScreen = false,
            task = listenMock,
        ) {}
    }
}

@SmallScreenPreview
@Composable
private fun SelectCorrectWidgetSmallAudio() {
    LCPreview {
        SelectCorrectWidget(
            isBigScreen = false,
            task = selectAudioMock,
        ) {}
    }
}

@SmallScreenPreview
@Composable
private fun SelectCorrectWidgetImageSmallPreview() {
    LCPreview {
        SelectCorrectWidget(
            isBigScreen = false,
            task = imageSelectWordTranslation,
        ) {}
    }
}

@SmallScreenPreview
@Composable
private fun SelectCorrectInThreeWidgetSmallPreview() {
    LCPreview {
        SelectCorrectWidget(
            isBigScreen = false,
            task = selectCorrectAnswerInThreeMock,
        ) {}
    }
}

@SmallScreenPreview
@Composable
private fun SelectCorrectFillInBlancWidgetSmallPreview() {
    LCPreview {
        SelectCorrectWidget(
            isBigScreen = false,
            task = selectCorrectAnswerFillInBlankMock,
        ) {}
    }
}
