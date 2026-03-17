package com.linguaceleris.quiz.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@OptIn(markerClass = [InternalSerializationApi::class])
@Serializable
data class ScheduleDTO(
    @SerialName("contract_version")
    val contractVersion: Int,
    val days: List<DayDTO>,
)
