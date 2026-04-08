package com.linguaceleris.quiz.model

import com.linguaceleris.network.serializer.LocalDateSerializer
import kotlinx.datetime.LocalDate
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(markerClass = [InternalSerializationApi::class])
@Serializable
data class DayDTO(
    val advanced: String?,
    val basic: String?,
    @Serializable(with = LocalDateSerializer::class)
    val date: LocalDate?,
    val intermediate: String?,
)
