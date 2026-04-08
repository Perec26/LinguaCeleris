package com.linguaceleris.designsystem.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun SystemBarsTheme(isDark: Boolean) {
    val view = LocalView.current

    SideEffect {
        val activity = view.context.findActivity()
        if (activity != null) {
            val window = activity.window
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !isDark
                isAppearanceLightNavigationBars = !isDark
            }
        }
    }
}

private tailrec fun Context.findActivity(): Activity? {
    if (this is Activity) return this
    if (this is ContextWrapper) return baseContext.findActivity()
    return null
}
