package com.linguaceleris.designsystem.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.theme.LCTheme

@Composable
fun DefaultHorizontalDivider(
    modifier: Modifier = Modifier,
    startPadding: Dp = 16.dp,
    endPadding: Dp = 16.dp,
) {
    HorizontalDivider(modifier = modifier.padding(start = startPadding, end = endPadding))
}

@PreviewLightDark
@Composable
private fun DefaultHorizontalDividerPreview() {
    LCTheme {
        DefaultHorizontalDivider()
    }
}