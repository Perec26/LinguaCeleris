package com.linguaceleris.quiz.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(markerClass = [InternalSerializationApi::class])
sealed class TaskDataDTO {

    abstract fun getImages(): List<String>

    @Serializable
    data class ChooseCorrectDTO(
        val question: VariantDTO,
        val answer: VariantDTO,
        val options: List<VariantDTO>,
    ) : TaskDataDTO() {

        override fun getImages(): List<String> {
            val images = options.map(VariantDTO::image) + question.image
            return images.filterNotNull()
        }
    }

    @Serializable
    data class MatchingDataDTO(
        val pairs: List<MatchingPairDTO>,
    ) : TaskDataDTO() {
        override fun getImages(): List<String> {
            val images = pairs.flatMap { listOf(it.left.image, it.right.image) }
            return images.filterNotNull()
        }
    }

    @Serializable
    data object UnknownDTO : TaskDataDTO() {
        override fun getImages(): List<String> = emptyList()
    }
}
