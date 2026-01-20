@file:Suppress("UnusedReceiverParameter")

package com.linguaceleris.designsystem.widgets

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun ColumnScope.SpacerHeight(
    height: Dp,
    modifier: Modifier = Modifier,
) = Spacer(modifier = modifier.height(height))

@Composable
fun RowScope.SpacerWidth(
    width: Dp,
    modifier: Modifier = Modifier,
) = Spacer(modifier = modifier.width(width))