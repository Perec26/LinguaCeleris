package com.linguaceleris.designsystem.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.R
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme

@Composable
fun DefaultFilledButton(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    text: String,
    isEnable: Boolean = true,
    onClick: () -> Unit,
) {
    HapticElement { haptic ->
        Button(
            modifier = modifier,
            enabled = isEnable,
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                onClick.invoke()
            },
        ) {
            Text(
                modifier = textModifier,
                text = text,
            )
        }
    }
}

@Composable
fun DefaultTextButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnable: Boolean = true,
    onClick: () -> Unit,
) {
    HapticElement { haptic ->

        TextButton(
            modifier = modifier,
            enabled = isEnable,
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                onClick.invoke()
            },
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
fun DefaultImageButton(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    @DrawableRes drawable: Int,
    isEnable: Boolean = true,
    onClick: () -> Unit,
) {
    HapticElement { haptic ->

        Button(
            modifier = modifier,
            contentPadding = PaddingValues(12.dp),
            shape = RoundedCornerShape(16.dp),
            enabled = isEnable,
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                onClick.invoke()
            },
        ) {
            Icon(
                modifier = iconModifier,
                painter = painterResource(drawable),
                contentDescription = null,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LCFilledButton(
    modifier: Modifier = Modifier,
    isEnable: Boolean = true,
    text: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    icon: Painter? = null,
    buttonSize: ButtonSize = ButtonSize.SMALL,
    onClick: () -> Unit,
) {
    val size = buttonSize.toContainerSize()

    HapticElement { haptic ->
        Button(
            modifier = modifier,
            enabled = isEnable,
            colors = colors,
            contentPadding = ButtonDefaults.contentPaddingFor(size, hasStartIcon = icon != null),
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                onClick.invoke()
            },
            content = { ButtonContent(icon, size, text) },
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LCTextButton(
    modifier: Modifier = Modifier,
    isEnable: Boolean = true,
    text: String,
    icon: Painter? = null,
    buttonSize: ButtonSize = ButtonSize.SMALL,
    onClick: () -> Unit,
) {
    val size = buttonSize.toContainerSize()

    HapticElement { haptic ->
        TextButton(
            modifier = modifier,
            enabled = isEnable,
            contentPadding = ButtonDefaults.contentPaddingFor(size, hasStartIcon = icon != null),
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                onClick.invoke()
            },
            content = { ButtonContent(icon, size, text) },
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LCFilledTonalButton(
    modifier: Modifier = Modifier,
    isEnable: Boolean = true,
    text: String,
    icon: Painter? = null,
    buttonSize: ButtonSize = ButtonSize.SMALL,
    onClick: () -> Unit,
) {
    val size = buttonSize.toContainerSize()

    HapticElement { haptic ->
        FilledTonalButton(
            modifier = modifier,
            enabled = isEnable,
            contentPadding = ButtonDefaults.contentPaddingFor(size, hasStartIcon = icon != null),
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                onClick.invoke()
            },
            content = { ButtonContent(icon, size, text) },
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LCOutlineButton(
    modifier: Modifier = Modifier,
    isEnable: Boolean = true,
    text: String,
    icon: Painter? = null,
    buttonSize: ButtonSize = ButtonSize.SMALL,
    onClick: () -> Unit,
) {
    val size = buttonSize.toContainerSize()

    HapticElement { haptic ->
        OutlinedButton(
            modifier = modifier,
            enabled = isEnable,
            contentPadding = ButtonDefaults.contentPaddingFor(size, hasStartIcon = icon != null),
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                onClick.invoke()
            },
            content = { ButtonContent(icon, size, text) },
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ButtonContent(icon: Painter? = null, size: Dp, text: String) {
    if (icon != null) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.iconSizeFor(size)),
        )
        Spacer(Modifier.size(ButtonDefaults.iconSpacingFor(size)))
    }
    Text(
        text = text,
        style = ButtonDefaults.textStyleFor(size),
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
enum class ButtonSize {
    SMALL,
    MEDIUM,
    LARGE,
    EXTRA_LARGE;

    fun toContainerSize() = when (this) {
        SMALL -> ButtonDefaults.MinHeight
        MEDIUM -> ButtonDefaults.MediumContainerHeight
        LARGE -> ButtonDefaults.LargeContainerHeight
        EXTRA_LARGE -> ButtonDefaults.ExtraLargeContainerHeight
    }
}

@PreviewLightDark
@Composable
private fun FilledButtonsPreview() {
    LinguaCelerisTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ButtonSize.entries.forEach {
                LCFilledButton(
                    text = "Filled button",
                    buttonSize = it,
                ) {}
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun FilledButtonsWithIconPreview() {
    LinguaCelerisTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ButtonSize.entries.forEach {
                LCFilledButton(
                    text = "Filled button with Icon",
                    icon = painterResource(R.drawable.email),
                    buttonSize = it,
                ) {}
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TextButtonsPreview() {
    LinguaCelerisTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ButtonSize.entries.forEach {
                LCTextButton(
                    text = "Text Button",
                    buttonSize = it,
                ) {}
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TextButtonsWithIconPreview() {
    LinguaCelerisTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ButtonSize.entries.forEach {
                LCTextButton(
                    text = "Text Button with Icon",
                    icon = painterResource(R.drawable.email),
                    buttonSize = it,
                ) {}
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun FilledTonalButtonsPreview() {
    LinguaCelerisTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ButtonSize.entries.forEach {
                LCFilledTonalButton(
                    text = "Filled Tonal Button",
                    buttonSize = it,
                ) {}
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun FilledTonalWithIconPreview() {
    LinguaCelerisTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ButtonSize.entries.forEach {
                LCFilledTonalButton(
                    text = "Filled Tonal Button with Icon",
                    icon = painterResource(R.drawable.email),
                    buttonSize = it,
                ) {}
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun OutlineButtonsPreview() {
    LinguaCelerisTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ButtonSize.entries.forEach {
                LCOutlineButton(
                    text = "OutLine Button",
                    buttonSize = it,
                ) {}
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun OutlineButtonsWithIconPreview() {
    LinguaCelerisTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ButtonSize.entries.forEach {
                LCOutlineButton(
                    text = "OutLine Button with Icon",
                    icon = painterResource(R.drawable.email),
                    buttonSize = it,
                ) {}
            }
        }
    }
}
