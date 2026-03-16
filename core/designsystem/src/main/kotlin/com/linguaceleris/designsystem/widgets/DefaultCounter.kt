package com.linguaceleris.designsystem.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.linguaceleris.designsystem.R
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme

@Composable
fun DefaultCounter(
    modifier: Modifier = Modifier,
    value: Int = 0,
    isPlusEnabled: Boolean = true,
    onPlusClick: () -> Unit = {},
    onMinusClick: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            enabled = value > 0,
            onClick = onMinusClick,
        ) {
            Icon(
                painter = painterResource(R.drawable.remove),
                contentDescription = "",
            )
        }

        Text(
            text = value.toString(),
            style = MaterialTheme.typography.bodyLarge,
        )

        IconButton(
            enabled = isPlusEnabled,
            onClick = onPlusClick,
        ) {
            Icon(
                painter = painterResource(R.drawable.add),
                contentDescription = "",
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun CounterPreview() {
    LinguaCelerisTheme {
        DefaultCounter()
    }
}
