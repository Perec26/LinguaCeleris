package com.linguaceleris.settings.impl.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.widgets.LCPreview

@Composable
internal fun SettingElement(
    title: String,
    value: Boolean,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .alpha(if (enabled) 1f else 0.38f)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = title)
        Switch(checked = value, enabled = enabled, onCheckedChange = null)
    }
}

@PreviewLightDark
@Composable
private fun SettingElementPreview() {
    LCPreview {
        Column {
            SettingElement(title = "Selected", value = true, onClick = {})
            SettingElement(title = "Not selected", value = false, onClick = {})
            SettingElement(title = "Disabled selected", value = true, enabled = false, onClick = {})
            SettingElement(
                title = "Disabled not selected",
                value = false,
                enabled = false,
                onClick = {},
            )
        }
    }
}
