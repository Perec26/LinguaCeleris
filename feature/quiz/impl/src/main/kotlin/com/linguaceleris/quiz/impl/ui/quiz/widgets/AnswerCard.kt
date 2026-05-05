package com.linguaceleris.quiz.impl.ui.quiz.widgets

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
import com.linguaceleris.quiz.impl.R

internal enum class CardState { DEFAULT, SELECTED, RIGHT, WRONG }

@Composable
internal fun AnswerCard(
    modifier: Modifier = Modifier,
    text: String = "",
    state: CardState = CardState.DEFAULT,
    enabled: Boolean = true,
    isAudio: Boolean = false,
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
                Icon(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(24.dp),
                    painter = painterResource(R.drawable.quiz_volume_up),
                    contentDescription = null,
                )
            } else {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = text,
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
    _root_ide_package_.com.linguaceleris.designsystem.widgets.LCPreview {
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
            AnswerCard(isAudio = true, state = CardState.SELECTED) {}
            AnswerCard(isAudio = true, state = CardState.RIGHT) {}
            AnswerCard(isAudio = true, state = CardState.WRONG) {}
            AnswerCard(isAudio = true) {}
        }
    }
}
