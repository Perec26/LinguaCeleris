package com.linguaceleris.designsystem.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.widgets.buttons.LCTextButton

@Composable
fun ThreeButtonsDialog(
    title: String,
    description: String,
    okButtonDescription: ButtonDescription? = null,
    noButtonDescription: ButtonDescription? = null,
    cancelButtonDescription: ButtonDescription? = null,
    onDismissRequest: () -> Unit,
) {
    DefaultDialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                cancelButtonDescription.ShowButton()
                SpacerWidth(modifier = Modifier.weight(1f), width = 1.dp)
                noButtonDescription.ShowButton()
                okButtonDescription.ShowButton()
            }
        }
    }
}

@Composable
private fun ButtonDescription?.ShowButton() = this?.let {
    LCTextButton(
        text = it.text,
        onClick = it.onClick,
    )
}

@PreviewLightDark
@Composable
private fun ThreeButtonsDialogPreview() {
    LCPreview {
        ThreeButtonsDialog(
            title = "Dialog title",
            description = "Dialog description",
            okButtonDescription = ButtonDescription("Ok"),
            noButtonDescription = ButtonDescription("No"),
            cancelButtonDescription = ButtonDescription("Cancel"),
        ) {}
    }
}

@PreviewLightDark
@Composable
private fun ThreeButtonsDialogLongDescriptionPreview() {
    LCPreview {
        ThreeButtonsDialog(
            title = "Dialog title",
            description = "Мы отправили письмо. Пожалуйста, перейдите по ссылке, чтобы войти.",
            okButtonDescription = ButtonDescription("Ok"),
            noButtonDescription = ButtonDescription("No"),
            cancelButtonDescription = ButtonDescription("Cancel"),
        ) {}
    }
}

data class ButtonDescription(val text: String, val onClick: () -> Unit = {})
