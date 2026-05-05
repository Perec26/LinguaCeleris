package com.linguaceleris.quiz.impl.ui.quiz.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.buttons.ButtonSize
import com.linguaceleris.designsystem.widgets.buttons.LCFilledButton
import com.linguaceleris.quiz.impl.R
import com.linguaceleris.quiz.impl.ui.quiz.matchingMock
import com.linguaceleris.quiz.impl.ui.quiz.model.AnswerType
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI
import com.linguaceleris.quiz.impl.ui.quiz.model.WordCardUI

@Composable
internal fun MatchWidget(
    modifier: Modifier = Modifier,
    task: TaskUI.Matching,
    onAudioClick: (String?) -> Unit = {},
    onContinueButtonClick: () -> Unit,
    onVariantSelected: (WordCardUI) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(task.type.text),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
        )

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MatchColumnWidget(
                modifier = Modifier.weight(1f),
                variants = task.originalVariants,
                hasError = task.hasError,
                errorVariant = task.errorVariant,
                selectedVariant = task.selectedVariant,
                correctVariant = task.correctVariant,
                disabledVariants = task.disabledVariants,
                isAudio = task.type.answerType == AnswerType.AUDIO,
                onAudioClick = onAudioClick,
                onVariantSelected = onVariantSelected,
            )
            MatchColumnWidget(
                modifier = Modifier.weight(1f),
                variants = task.translationVariants,
                hasError = task.hasError,
                errorVariant = task.errorVariant,
                selectedVariant = task.selectedVariant,
                correctVariant = task.correctVariant,
                disabledVariants = task.disabledVariants,
                onAudioClick = onAudioClick,
                onVariantSelected = onVariantSelected,
            )
        }

        LCFilledButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(R.string.quiz_continue),
            buttonSize = ButtonSize.MEDIUM,
            isEnable = task.hasError || task.isDone,
            onClick = onContinueButtonClick,
        )
    }
}

@PreviewLightDark
@Composable
private fun MatchWidgetPreview() {
    LCPreview {
        MatchWidget(
            task = matchingMock,
            onContinueButtonClick = {},
        ) {}
    }
}
