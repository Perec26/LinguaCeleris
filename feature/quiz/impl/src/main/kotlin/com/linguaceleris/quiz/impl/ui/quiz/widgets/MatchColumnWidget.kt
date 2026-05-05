package com.linguaceleris.quiz.impl.ui.quiz.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.quiz.impl.ui.quiz.fourEnVariantsMock
import com.linguaceleris.quiz.impl.ui.quiz.model.WordCardUI

@Composable
internal fun MatchColumnWidget(
    modifier: Modifier = Modifier,
    hasError: Boolean = false,
    selectedVariant: WordCardUI? = null,
    correctVariant: WordCardUI? = null,
    errorVariant: WordCardUI? = null,
    variants: List<WordCardUI>,
    disabledVariants: List<WordCardUI> = emptyList(),
    isAudio: Boolean = false,
    onAudioClick: (String?) -> Unit = {},
    onVariantSelected: (WordCardUI) -> Unit,
) {
    val haptic = LocalHapticFeedback.current

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
            AnswerCard(
                state = state,
                enabled = !disabledVariants.contains(variant) && !hasError,
                text = variant.text,
                isAudio = isAudio,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    onAudioClick(variant.audio)
                    onVariantSelected(variant)
                },
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun MatchColumnWidgetPreview() {
    LCPreview {
        MatchColumnWidget(
            selectedVariant = WordCardUI("audio", "bat"),
            variants = fourEnVariantsMock,
            onVariantSelected = {},
        )
    }
}
