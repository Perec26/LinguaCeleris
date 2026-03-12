package com.linguaceleris.quiz.impl.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme
import com.linguaceleris.designsystem.widgets.CardState
import com.linguaceleris.designsystem.widgets.LCCardWithText
import com.linguaceleris.quiz.impl.ui.fourEnVariantsMock
import com.linguaceleris.quiz.impl.ui.model.WordCardUI

@Composable
internal fun MatchColumnWidget(
    modifier: Modifier = Modifier,
    hasError: Boolean = false,
    selectedVariant: WordCardUI? = null,
    correctVariant: WordCardUI? = null,
    errorVariant: WordCardUI? = null,
    variants: List<WordCardUI>,
    disabledVariants: List<WordCardUI> = emptyList(),
    onVariantSelected: (WordCardUI) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        for (variant in variants) {
            val state = when {
                variant == errorVariant -> CardState.WRONG
                variant == selectedVariant -> CardState.SELECTED
                hasError && variant == correctVariant -> CardState.RIGHT
                else -> CardState.DEFAULT
            }
            LCCardWithText(
                state = state,
                enabled = !disabledVariants.contains(variant) && !hasError,
                text = variant.text,
                onClick = { onVariantSelected(variant) },
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun MatchColumnWidgetPreview() {
    LinguaCelerisTheme {
        MatchColumnWidget(
            selectedVariant = WordCardUI("audio", "bat"),
            variants = fourEnVariantsMock,
            onVariantSelected = {},
        )
    }
}
