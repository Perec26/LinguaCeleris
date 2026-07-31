@file:PendingUiTests

package com.linguaceleris.settings.impl.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.SmallScreenPreview
import com.linguaceleris.designsystem.widgets.SpacerWidth
import com.linguaceleris.testing.PendingUiTests

@Composable
internal fun SettingInfoElement(title: String, value: String, onClick: () -> Unit = {},) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            maxLines = 2,
            text = title,
        )
        SpacerWidth(8.dp)
        Text(
            maxLines = 1,
            text = value,
        )
    }
}

@SmallScreenPreview
@PreviewLightDark
@Composable
private fun SettingInfoElementPreview() {
    LCPreview {
        Column {
            SettingInfoElement(title = "Version", value = "150.02.1548")
        }
    }
}
