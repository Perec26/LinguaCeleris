package com.linguaceleris.designsystem.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
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
    Button(
        modifier = modifier,
        enabled = isEnable,
        onClick = onClick,
    ) {
        Text(
            modifier = textModifier,
            text = text,
        )
    }
}

@Composable
fun DefaultTextButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnable: Boolean = true,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = modifier,
        enabled = isEnable,
        onClick = onClick,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
        )
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
    Button(
        modifier = modifier,
        contentPadding = PaddingValues(12.dp),
        shape = RoundedCornerShape(16.dp),
        enabled = isEnable,
        onClick = onClick,
    ) {
        Icon(
            modifier = iconModifier,
            painter = painterResource(drawable),
            contentDescription = null,
        )
    }
}

@PreviewLightDark
@Composable
private fun ButtonsPreview() {
    LinguaCelerisTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            DefaultFilledButton(text = "Text") {}
            DefaultTextButton(text = "Text") {}
            DefaultImageButton(drawable = R.drawable.add) {}
        }
    }
}
