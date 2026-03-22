package com.linguaceleris.designsystem.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.platform.LocalHapticFeedback

@Composable
fun HapticElement(content: @Composable (HapticFeedback) -> Unit) {
    val haptic = LocalHapticFeedback.current
    content(haptic)
}
