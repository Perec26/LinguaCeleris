package com.linguaceleris.quiz.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SelectTranslationData(
    val question: String,
    val answer: String,
    val alternatives: List<String>,
    @SerialName("image_path")
    val imagePath: String,
    @SerialName("audio_en")
    val audioEn: String,
    @SerialName("audio_ru")
    val audioRu: String,
)
