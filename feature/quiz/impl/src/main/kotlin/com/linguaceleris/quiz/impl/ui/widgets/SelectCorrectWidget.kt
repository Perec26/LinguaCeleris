package com.linguaceleris.quiz.impl.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme
import com.linguaceleris.designsystem.widgets.CardState
import com.linguaceleris.designsystem.widgets.DefaultFilledButton
import com.linguaceleris.designsystem.widgets.LCCardWithText
import com.linguaceleris.quiz.impl.R
import com.linguaceleris.quiz.impl.ui.model.TaskUI
import com.linguaceleris.quiz.impl.ui.model.WordCardUI
import com.linguaceleris.quiz.impl.ui.selectCorrectAnswerMock

@Composable
internal fun SelectCorrectWidget(
    modifier: Modifier = Modifier,
    task: TaskUI.SelectCorrectAnswer,
    onVariantSelected: (WordCardUI) -> Unit = {},
    onCheckButtonClick: () -> Unit = {},
    onContinueButtonClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(task.text),
        )

        Text(
            modifier = Modifier.padding(16.dp),
            text = task.question.text,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge,
        )

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            LazyVerticalGrid(
                modifier = Modifier,
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
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

                    LCCardWithText(
                        state = state,
                        enabled = !task.isChecked || isSelected,
                        text = variant.text,
                        onClick = { onVariantSelected(variant) },
                    )
                }
            }
        }

        if (!task.isChecked) {
            DefaultFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
private fun SelectCorrectWidgetTestPreview() {
    LinguaCelerisTheme {
        Surface {
            SelectCorrectWidget(
                task = selectCorrectAnswerMock,
            ) {}
        }
    }
}
