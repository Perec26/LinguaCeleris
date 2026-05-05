package com.linguaceleris.designsystem.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.R
import com.linguaceleris.designsystem.widgets.buttons.LCFilledButton

@Composable
fun CommonErrorWidget(onRefreshClick: () -> Unit) {
    TransparentSurface {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        ) {
            Text(
                text = stringResource(R.string.error_title),
                style = MaterialTheme.typography.headlineMedium,
            )

            Text(
                text = stringResource(R.string.error_description),
                style = MaterialTheme.typography.bodyMedium,
            )

            LCFilledButton(
                text = stringResource(R.string.error_button),
                onClick = onRefreshClick,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun CommonErrorWidgetPreview() {
    LCPreview {
        CommonErrorWidget {}
    }
}
