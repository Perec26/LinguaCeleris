@file:PendingUiTests

package com.linguaceleris.quiz.impl.ui.quiz.widgets

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.theme.disabled
import com.linguaceleris.designsystem.theme.extendedColors
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.quiz.impl.R
import com.linguaceleris.testing.ExcludeFromKover
import com.linguaceleris.testing.PendingUiTests

@ExcludeFromKover
internal enum class CardState { DEFAULT, SELECTED, RIGHT, WRONG }

@Composable
internal fun AnswerCard(
    modifier: Modifier = Modifier,
    text: String = "",
    state: CardState = CardState.DEFAULT,
    enabled: Boolean = true,
    isAudio: Boolean = false,
    bigSize: Boolean = true,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        colors = getColors(state),
        enabled = enabled,
        onClick = onClick,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            if (isAudio) {
                val padding = if (bigSize) 16.dp else 12.dp
                Icon(
                    modifier = Modifier
                        .padding(padding)
                        .size(24.dp),
                    painter = painterResource(R.drawable.quiz_volume_up),
                    contentDescription = null,
                )
            } else {
                val style = if (bigSize) {
                    MaterialTheme.typography.bodyLarge
                } else {
                    MaterialTheme.typography.bodyMedium
                }
                val padding = if (bigSize) 16.dp else 8.dp
                Text(
                    modifier = Modifier
                        .basicMarquee()
                        .padding(padding),
                    text = text,
                    maxLines = 1,
                    style = style,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun getColors(state: CardState): CardColors {
    val colorScheme = MaterialTheme.colorScheme
    val extendedColors = MaterialTheme.extendedColors

    return when (state) {
        CardState.DEFAULT -> getCardColor(colorScheme.surfaceContainerHighest)
        CardState.SELECTED -> getCardColor(containerColor = colorScheme.primaryContainer)
        CardState.RIGHT -> getCardColor(containerColor = extendedColors.green.colorContainer)
        CardState.WRONG -> getCardColor(containerColor = extendedColors.red.colorContainer)
    }
}

@Composable
private fun getCardColor(containerColor: Color) = CardDefaults.cardColors().copy(
    containerColor = containerColor,
    contentColor = contentColorFor(containerColor),
    disabledContainerColor = containerColor.disabled(),
    disabledContentColor = contentColorFor(containerColor).disabled(),
)

@PreviewLightDark
@Composable
private fun AnswerCardPreview() {
    LCPreview {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnswerCard(modifier = Modifier.fillMaxWidth(), text = "Preview") {}
            AnswerCard(text = "Selected Preview", state = CardState.SELECTED) {}
            AnswerCard(text = "Right Preview", state = CardState.RIGHT) {}
            AnswerCard(text = "Wrong Preview", state = CardState.WRONG) {}
            AnswerCard(text = "Disabled Preview") {}
            AnswerCard(bigSize = false, modifier = Modifier.fillMaxWidth(), text = "Preview") {}
            AnswerCard(bigSize = false, text = "Selected Preview", state = CardState.SELECTED) {}
            AnswerCard(bigSize = false, text = "Right Preview", state = CardState.RIGHT) {}
            AnswerCard(bigSize = false, text = "Wrong Preview", state = CardState.WRONG) {}
            AnswerCard(bigSize = false, text = "Disabled Preview") {}
        }
    }
}

@PreviewLightDark
@Composable
private fun AnswerCardIconPreview() {
    LCPreview {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnswerCard(isAudio = true, state = CardState.SELECTED) {}
            AnswerCard(isAudio = true, state = CardState.RIGHT) {}
            AnswerCard(isAudio = true, state = CardState.WRONG) {}
            AnswerCard(isAudio = true) {}
            AnswerCard(bigSize = false, isAudio = true, state = CardState.SELECTED) {}
            AnswerCard(bigSize = false, isAudio = true, state = CardState.RIGHT) {}
            AnswerCard(bigSize = false, isAudio = true, state = CardState.WRONG) {}
            AnswerCard(bigSize = false, isAudio = true) {}
        }
    }
}
