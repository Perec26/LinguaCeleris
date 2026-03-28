package com.linguaceleris.quiz.impl.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.quiz.impl.R

@Composable
internal fun LivesIndicator(totalLives: Int = 3, remainingLives: Int = 3) {
    Row {
        repeat(totalLives) { count ->
            val color = if (count < remainingLives) {
                ColorFilter.tint(MaterialTheme.colorScheme.error)
            } else {
                ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            }
            Image(
                painter = painterResource(id = R.drawable.quiz_favorite),
                colorFilter = color,
                contentDescription = null,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun LivesIndicatorPreview() {
    LCPreview {
        Column {
            LivesIndicator(remainingLives = 3)
            LivesIndicator(remainingLives = 2)
            LivesIndicator(remainingLives = 1)
            LivesIndicator(remainingLives = 0)
        }
    }
}
