package com.linguaceleris.quiz.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(markerClass = [InternalSerializationApi::class])
@Serializable(with = TaskSerializer::class)
sealed class TaskDTO {

    abstract val id: String
    abstract val data: TaskDataDTO

    @Serializable
    data class AudioMatchingDTO(
        override val id: String,
        override val data: TaskDataDTO.MatchingDataDTO,
    ) : TaskDTO()

    @Serializable
    data class AntonymChoiceDTO(
        override val id: String,
        override val data: TaskDataDTO.ChooseCorrectDTO,
    ) : TaskDTO()

    @Serializable
    data class FillInTheBlankDTO(
        override val id: String,
        override val data: TaskDataDTO.ChooseCorrectDTO,
    ) : TaskDTO()

    @Serializable
    data class FindCorrectDTO(
        override val id: String,
        override val data: TaskDataDTO.ChooseCorrectDTO,
    ) : TaskDTO()

    @Serializable
    data class HomophonesDTO(
        override val id: String,
        override val data: TaskDataDTO.ChooseCorrectDTO,
    ) : TaskDTO()

    @Serializable
    data class ImageSelectWordTranslationDTO(
        override val id: String,
        override val data: TaskDataDTO.ChooseCorrectDTO,
    ) : TaskDTO()

    @Serializable
    data class ListenSelectTranslationDTO(
        override val id: String,
        override val data: TaskDataDTO.ChooseCorrectDTO,
    ) : TaskDTO()

    @Serializable
    data class MatchingDTO(
        override val id: String,
        override val data: TaskDataDTO.MatchingDataDTO,
    ) : TaskDTO()

    @Serializable
    data class SelectAudioDTO(
        override val id: String,
        override val data: TaskDataDTO.ChooseCorrectDTO,
    ) : TaskDTO()

    @Serializable
    data class SelectTranslationDTO(
        override val id: String,
        override val data: TaskDataDTO.ChooseCorrectDTO,
    ) : TaskDTO()

    @Serializable
    data class SynonymChoiceDTO(
        override val id: String,
        override val data: TaskDataDTO.ChooseCorrectDTO,
    ) : TaskDTO()

    @Serializable
    data class UnknowQuestionDTO(
        override val id: String,
        override val data: TaskDataDTO.UnknownDTO,
    ) : TaskDTO()
}
