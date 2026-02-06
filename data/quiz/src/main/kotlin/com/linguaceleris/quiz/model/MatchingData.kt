package com.linguaceleris.quiz.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchingData(
    val pairs: List<MatchingPair>,
)
