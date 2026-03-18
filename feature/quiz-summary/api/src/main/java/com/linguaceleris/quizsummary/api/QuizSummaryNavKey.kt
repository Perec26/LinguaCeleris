package com.linguaceleris.quizsummary.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class QuizSummaryNavKey(
    val isSuccessful: Boolean,
) : NavKey
