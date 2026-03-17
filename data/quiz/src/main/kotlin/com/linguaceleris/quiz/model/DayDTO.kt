package com.linguaceleris.quiz.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(markerClass = [InternalSerializationApi::class])
@Serializable
data class DayDTO(
    val advanced: String?,
    val basic: String?,
    val date: String,
    val intermediate: String?,
)
