package com.linguaceleris.designsystem.widgets.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.R
import com.linguaceleris.designsystem.widgets.HapticElement
import com.linguaceleris.designsystem.widgets.LCPreview

@Composable
fun LCFilledImageButton(
    modifier: Modifier = Modifier,
    @DrawableRes drawable: Int,
    isEnable: Boolean = true,
    buttonSize: ButtonSize = ButtonSize.SMALL,
    iconWith: IconWidth = IconWidth.Uniform,
    contentDescription: String = "",
    onClick: () -> Unit,
) {
    HapticElement { haptic ->
        FilledIconButton(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                onClick.invoke()
            },
            enabled = isEnable,
            modifier = modifier.size(buttonSize.toIconContainerSize(iconWith)),
            shape = buttonSize.getShape(),
        ) {
            Icon(
                painter = painterResource(drawable),
                contentDescription = contentDescription,
                modifier = Modifier.size(buttonSize.toIconSize()),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun FilledImageButtonPreview() {
    ButtonPreview {
        LCFilledImageButton(
            drawable = R.drawable.email,
            buttonSize = it,
        ) {}
    }
}

@Composable
private fun ButtonPreview(content: @Composable (ButtonSize) -> Unit) {
    LCPreview {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ButtonSize.entries.forEach {
                content(it)
            }
        }
    }
}
