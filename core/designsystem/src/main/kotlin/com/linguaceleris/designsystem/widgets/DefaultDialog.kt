package com.linguaceleris.designsystem.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun DefaultDialog(onDismissRequest: () -> Unit, content: @Composable () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier.padding(24.dp),
            shape = RoundedCornerShape(28.dp),
            // несоответствие цветов Card в адроиде и Dialog в материале 3
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                disabledContentColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            ),
        ) {
            content()
        }
    }
}

@PreviewLightDark
@Composable
private fun DefaultDialogPreview() {
    LCPreview {
        DefaultDialog(
            onDismissRequest = {},
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Dialog Example",
            )
        }
    }
}
