package com.linguaceleris.quiz.impl.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme
import com.linguaceleris.designsystem.widgets.DefaultFilledButton
import com.linguaceleris.quiz.impl.R
import com.linguaceleris.quiz.impl.ui.matchingMock
import com.linguaceleris.quiz.impl.ui.model.TaskUI
import com.linguaceleris.quiz.impl.ui.model.WordCardUI

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

        DefaultFilledButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textModifier = Modifier.padding(8.dp),
            text = stringResource(R.string.quiz_continue),
            isEnable = task.hasError || task.isDone,
            onClick = onContinueButtonClick,
        )
    }
}

@PreviewLightDark
@Composable
private fun MatchWidgetPreview() {
    LinguaCelerisTheme {
        Surface {
            MatchWidget(
                task = matchingMock,
                onContinueButtonClick = {},
            ) {}
        }
    }
}
