package com.linguaceleris.designsystem.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.theme.disabled
import com.linguaceleris.designsystem.theme.extendedColors

enum class CardState { DEFAULT, SELECTED, RIGHT, WRONG }

@Composable
fun LCCardWithText(
    modifier: Modifier = Modifier,
    text: String,
    state: CardState = CardState.DEFAULT,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme
    val extendedColors = MaterialTheme.extendedColors

    val colors = when (state) {
        CardState.DEFAULT -> CardDefaults.cardColors()

        CardState.SELECTED -> {
            CardDefaults.cardColors().copy(
                containerColor = colorScheme.primaryContainer,
                contentColor = colorScheme.onPrimaryContainer,
                disabledContainerColor = colorScheme.primaryContainer.disabled(),
                disabledContentColor = colorScheme.onPrimaryContainer.disabled(),
            )
        }

        CardState.RIGHT -> {
            CardDefaults.cardColors().copy(
                containerColor = extendedColors.green.colorContainer,
                contentColor = extendedColors.green.onColorContainer,
                disabledContainerColor = extendedColors.green.colorContainer.disabled(),
                disabledContentColor = extendedColors.green.onColorContainer.disabled(),
            )
        }

        CardState.WRONG -> {
            CardDefaults.cardColors().copy(
                containerColor = colorScheme.errorContainer,
                contentColor = colorScheme.onErrorContainer,
                disabledContainerColor = colorScheme.errorContainer.disabled(),
                disabledContentColor = colorScheme.onErrorContainer.disabled(),
            )
        }
    }

    Card(
        modifier = modifier,
        colors = colors,
        enabled = enabled,
        onClick = onClick,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = text,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun LCCardWithTextPreview() {
    LCPreview {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LCCardWithText(modifier = Modifier.fillMaxWidth(), text = "Preview") {}
            LCCardWithText(text = "Selected Preview", state = CardState.SELECTED) {}
            LCCardWithText(text = "Right Preview", state = CardState.RIGHT) {}
            LCCardWithText(text = "Wrong Preview", state = CardState.WRONG) {}
            LCCardWithText(text = "Disabled Preview") {}
        }
    }
}
