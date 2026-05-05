package com.linguaceleris.designsystem.widgets.buttons

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

internal typealias IconWidth = IconButtonDefaults.IconButtonWidthOption

enum class ButtonSize {
    SMALL,
    MEDIUM,
    LARGE,
    EXTRA_LARGE
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun ButtonSize.toContainerSize() = when (this) {
    ButtonSize.SMALL -> ButtonDefaults.MinHeight
    ButtonSize.MEDIUM -> ButtonDefaults.MediumContainerHeight
    ButtonSize.LARGE -> ButtonDefaults.LargeContainerHeight
    ButtonSize.EXTRA_LARGE -> ButtonDefaults.ExtraLargeContainerHeight
}

@Composable
fun ButtonSize.getShape() = when (this) {
    ButtonSize.SMALL -> MaterialTheme.shapes.small
    ButtonSize.MEDIUM -> MaterialTheme.shapes.medium
    ButtonSize.LARGE -> MaterialTheme.shapes.large
    ButtonSize.EXTRA_LARGE -> MaterialTheme.shapes.extraLarge
}

fun ButtonSize.toIconContainerSize(wideOption: IconWidth = IconWidth.Uniform) = when (this) {
    ButtonSize.SMALL -> IconButtonDefaults.smallContainerSize(wideOption)
    ButtonSize.MEDIUM -> IconButtonDefaults.mediumContainerSize(wideOption)
    ButtonSize.LARGE -> IconButtonDefaults.largeContainerSize(wideOption)
    ButtonSize.EXTRA_LARGE -> IconButtonDefaults.extraLargeContainerSize(wideOption)
}

fun ButtonSize.toIconSize() = when (this) {
    ButtonSize.SMALL -> IconButtonDefaults.smallIconSize
    ButtonSize.MEDIUM -> IconButtonDefaults.mediumIconSize
    ButtonSize.LARGE -> IconButtonDefaults.largeIconSize
    ButtonSize.EXTRA_LARGE -> IconButtonDefaults.extraLargeIconSize
}
