package com.linguaceleris.designsystem.widgets

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private const val FRACTION = 0.25f

object Gradients {

    fun Color.lighten(): Color = Color(
        red = (red + (1f - red) * FRACTION).coerceIn(0f, 1f),
        green = (green + (1f - green) * FRACTION).coerceIn(0f, 1f),
        blue = (blue + (1f - blue) * FRACTION).coerceIn(0f, 1f),
        alpha = alpha,
    )

    fun Color.darken(): Color = Color(
        red = (red * (1f - FRACTION)).coerceIn(0f, 1f),
        green = (green * (1f - FRACTION)).coerceIn(0f, 1f),
        blue = (blue * (1f - FRACTION)).coerceIn(0f, 1f),
        alpha = alpha,
    )

    fun Color.toLightBaseGradient(): Brush = Brush.horizontalGradient(listOf(lighten(), this))
    fun Color.toDarkBaseGradient(): Brush = Brush.horizontalGradient(listOf(darken(), this))
}
