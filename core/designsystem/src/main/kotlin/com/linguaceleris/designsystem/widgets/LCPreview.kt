package com.linguaceleris.designsystem.widgets

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme

@Composable
fun LCPreview(content: @Composable () -> Unit) {
    LinguaCelerisTheme {
        Surface {
            content()
        }
    }
}
