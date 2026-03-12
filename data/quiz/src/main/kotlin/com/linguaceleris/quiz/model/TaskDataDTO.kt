package com.linguaceleris.quiz.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(markerClass = [InternalSerializationApi::class])
sealed class TaskDataDTO {

    @Serializable
    data class ChooseCorrectDTO(
        val question: VariantDTO,
        val answer: VariantDTO,
        val options: List<VariantDTO>,
    ) : TaskDataDTO()

    @Serializable
    data class MatchingDataDTO(
        val pairs: List<MatchingPairDTO>,
    ) : TaskDataDTO()

    @Serializable
    data object UnknownDTO : TaskDataDTO()
}
