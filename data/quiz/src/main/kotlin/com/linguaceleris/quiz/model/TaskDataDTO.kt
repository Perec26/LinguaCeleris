package com.linguaceleris.quiz.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(markerClass = [InternalSerializationApi::class])
sealed class TaskDataDTO {

    abstract fun getImages(): List<String>
    abstract fun getAudios(): List<String>

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

        override fun getAudios(): List<String> {
            val audios = options.map(VariantDTO::audio) + question.audio
            return audios.filterNotNull()
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

        override fun getAudios(): List<String> {
            val audios = pairs.flatMap { listOf(it.left.audio, it.right.audio) }
            return audios.filterNotNull()
        }
    }

    @Serializable
    data object UnknownDTO : TaskDataDTO() {
        override fun getImages(): List<String> = emptyList()
        override fun getAudios(): List<String> = emptyList()
    }
}
