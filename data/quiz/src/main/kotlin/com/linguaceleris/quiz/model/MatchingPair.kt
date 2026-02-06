package com.linguaceleris.quiz.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchingPair(
    @SerialName("audio_en")
    val audioEn: String,
    @SerialName("audio_ru")
    val audioRu: String,
    val en: String,
    val ru: String
)
