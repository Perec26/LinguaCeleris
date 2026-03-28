package com.linguaceleris.designsystem.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun CheckBoxWithText(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    text: String,
    isEnable: Boolean = true,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.padding(end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = isChecked,
            enabled = isEnable,
            onCheckedChange = { onClick() },
        )
        Text(
            modifier = Modifier.clickable(onClick = onClick),
            text = text,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@PreviewLightDark
@Composable
private fun CheckBoxWithTextPreview() {
    LCPreview {
        Column {
            CheckBoxWithText(isChecked = true, text = "CheckBox checked") {}
            CheckBoxWithText(isChecked = false, text = "CheckBox unchecked") {}
            CheckBoxWithText(
                isChecked = true,
                isEnable = false,
                text = "CheckBox checked unable",
            ) {}
            CheckBoxWithText(
                isChecked = false,
                isEnable = false,
                text = "CheckBox unchecked unable",
            ) {}
        }
    }
}
