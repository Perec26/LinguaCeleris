package com.linguaceleris.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

val LocalExtendedColors = staticCompositionLocalOf { ExtendedColorScheme() }

val MaterialTheme.extendedColors: ExtendedColorScheme
    @Composable
    @ReadOnlyComposable
    get() = LocalExtendedColors.current

@Composable
fun LinguaCelerisTheme(
    useSystemTheme: Boolean = true,
    useDarkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    val isSystemInDarkTheme: Boolean = isSystemInDarkTheme()

    val isDark = when {
        useSystemTheme && isSystemInDarkTheme -> true
        !useSystemTheme && useDarkTheme -> true
        else -> false
    }

    val colorScheme = if (isDark) darkScheme else lightScheme
    val extendedColors = if (isDark) extendedDark else extendedLight

    SystemBarsTheme(isDark)

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = LCTypography,
            content = content,
        )
    }
}

// TODO: Подумать надо ли оно
val appShapes = Shapes(
    extraSmall = CutCornerShape(8.dp),
    small = CutCornerShape(12.dp),
    medium = CutCornerShape(16.dp),
    large = CutCornerShape(24.dp),
    extraLarge = CutCornerShape(36.dp),
)
