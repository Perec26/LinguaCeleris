package com.linguaceleris.auth.impl.ui.signin.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.linguaceleris.auth.impl.R
import com.linguaceleris.designsystem.widgets.HapticElement
import com.linguaceleris.designsystem.widgets.LCPreview

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun GoogleSignInButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val size = ButtonDefaults.MediumContainerHeight
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color(0xFF131314) else Color(0xFFFFFFFF)
    val textColor = if (isDarkTheme) Color(0xFFE3E3E3) else Color(0xFF1F1F1F)
    val borderColor = if (isDarkTheme) Color(0xFF8E918F) else Color(0xFF747775)

    val colors = ButtonDefaults.buttonColors().copy(
        containerColor = backgroundColor,
        contentColor = textColor,
    )

    HapticElement { haptic ->
        Button(
            modifier = modifier,
            colors = colors,
            contentPadding = ButtonDefaults.contentPaddingFor(size, hasStartIcon = true),
            border = BorderStroke(1.dp, borderColor),
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                onClick.invoke()
            },
        ) {
            Image(
                painter = painterResource(R.drawable.auth_google_logo),
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.iconSizeFor(size)),
            )

            Spacer(Modifier.size(ButtonDefaults.iconSpacingFor(size)))

            Text(
                text = stringResource(R.string.auth_google_sign_in),
                style = ButtonDefaults.textStyleFor(size),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun GoogleSignInButtonPreview() {
    LCPreview {
        GoogleSignInButton(
            modifier = Modifier.padding(16.dp),
        ) {}
    }
}
