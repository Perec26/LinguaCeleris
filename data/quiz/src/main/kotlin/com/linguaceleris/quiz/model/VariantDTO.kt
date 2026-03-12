package com.linguaceleris.quiz.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(markerClass = [InternalSerializationApi::class])
@Serializable
data class VariantDTO(
    val audio: String?,
    val text: String,
    val image: String?,
)
