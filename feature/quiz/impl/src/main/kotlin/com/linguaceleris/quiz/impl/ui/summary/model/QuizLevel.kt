package com.linguaceleris.quiz.impl.ui.summary.model

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.linguaceleris.designsystem.theme.buttonColors
import com.linguaceleris.designsystem.theme.extendedColors
import com.linguaceleris.quiz.api.QuizLevel
import com.linguaceleris.quiz.impl.R

@Composable
internal fun QuizLevel.buttonColors() = when (this) {
    QuizLevel.BASIC -> MaterialTheme.extendedColors.green.buttonColors()
    QuizLevel.INTERMEDIATE -> MaterialTheme.extendedColors.yellow.buttonColors()
    QuizLevel.ADVANCED -> MaterialTheme.extendedColors.red.buttonColors()
}

@get:StringRes
internal val QuizLevel.title: Int
    get() {
        return when (this) {
            QuizLevel.BASIC -> R.string.quiz_summary_level_basic
            QuizLevel.INTERMEDIATE -> R.string.quiz_summary_level_intermediate
            QuizLevel.ADVANCED -> R.string.quiz_summary_level_advanced
        }
    }
