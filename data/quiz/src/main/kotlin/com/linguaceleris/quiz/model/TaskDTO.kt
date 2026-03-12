package com.linguaceleris.quiz.model

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

private const val SELECT_TRANSLATION_EN_TYPE_NAME = "select_translation"
private const val MATCHING_TYPE_NAME = "matching"
private const val HOMOPHONES_TYPE_NAME = "homophones"
private const val FIND_CORRECT_TYPE_NAME = "find_correct"
private const val FILL_IN_BLANK_TYPE_NAME = "fill_in_blank"
private const val SYNONYM_CHOICE_TYPE_NAME = "synonym_choice"
private const val ANTONYM_CHOICE_TYPE_NAME = "antonym_choice"
private const val AUDIO_MATCHING_TYPE_NAME = "audio_matching"
private const val LISTEN_SELECT_TRANSLATION_TYPE_NAME = "listen_select_translation"
private const val IMAGE_SELECT_WORD_TRANSLATION_TYPE_NAME = "image_select_word"

@OptIn(markerClass = [InternalSerializationApi::class])
@Serializable(with = TaskSerializer::class)
sealed class TaskDTO {

    abstract val id: String
    abstract val type: String
    abstract val data: TaskDataDTO

    @Serializable
    data class SelectTranslationDTO(
        override val id: String,
        override val type: String = SELECT_TRANSLATION_EN_TYPE_NAME,
        override val data: TaskDataDTO.ChooseCorrectDTO,
    ) : TaskDTO()

    @Serializable
    data class MatchingDTO(
        override val id: String,
        override val type: String = MATCHING_TYPE_NAME,
        override val data: TaskDataDTO.MatchingDataDTO,
    ) : TaskDTO()

    @Serializable
    data class HomophonesDTO(
        override val id: String,
        override val type: String = HOMOPHONES_TYPE_NAME,
        override val data: TaskDataDTO.ChooseCorrectDTO,
    ) : TaskDTO()

    @Serializable
    data class FindCorrectDTO(
        override val id: String,
        override val type: String = FIND_CORRECT_TYPE_NAME,
        override val data: TaskDataDTO.ChooseCorrectDTO,
    ) : TaskDTO()

    @Serializable
    data class FillInTheBlankDTO(
        override val id: String,
        override val type: String = FILL_IN_BLANK_TYPE_NAME,
        override val data: TaskDataDTO.ChooseCorrectDTO,
    ) : TaskDTO()

    @Serializable
    data class SynonymChoiceDTO(
        override val id: String,
        override val type: String = SYNONYM_CHOICE_TYPE_NAME,
        override val data: TaskDataDTO.ChooseCorrectDTO,
    ) : TaskDTO()

    @Serializable
    data class AntonymChoiceDTO(
        override val id: String,
        override val type: String = ANTONYM_CHOICE_TYPE_NAME,
        override val data: TaskDataDTO.ChooseCorrectDTO,
    ) : TaskDTO()

    @Serializable
    data class AudioMatchingDTO(
        override val id: String,
        override val type: String = AUDIO_MATCHING_TYPE_NAME,
        override val data: TaskDataDTO.MatchingDataDTO,
    ) : TaskDTO()

    @Serializable
    data class ListenSelectTranslationDTO(
        override val id: String,
        override val type: String = LISTEN_SELECT_TRANSLATION_TYPE_NAME,
        override val data: TaskDataDTO.ChooseCorrectDTO,
    ) : TaskDTO()

    @Serializable
    data class ImageSelectWordTranslationDTO(
        override val id: String,
        override val type: String = IMAGE_SELECT_WORD_TRANSLATION_TYPE_NAME,
        override val data: TaskDataDTO.ChooseCorrectDTO,
    ) : TaskDTO()

    @Serializable
    data class UnknowQuestionDTO(
        override val id: String,
        override val type: String,
        override val data: TaskDataDTO.UnknownDTO,
    ) : TaskDTO()
}

internal object TaskSerializer :
    JsonContentPolymorphicSerializer<TaskDTO>(TaskDTO::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<TaskDTO> {
        val type = element.jsonObject.getValue("type").jsonPrimitive.content
        return when (type) {
            SELECT_TRANSLATION_EN_TYPE_NAME -> TaskDTO.SelectTranslationDTO.serializer()

            MATCHING_TYPE_NAME -> TaskDTO.MatchingDTO.serializer()

            HOMOPHONES_TYPE_NAME -> TaskDTO.HomophonesDTO.serializer()

            FIND_CORRECT_TYPE_NAME -> TaskDTO.FindCorrectDTO.serializer()

            FILL_IN_BLANK_TYPE_NAME -> TaskDTO.FillInTheBlankDTO.serializer()

            SYNONYM_CHOICE_TYPE_NAME -> TaskDTO.SynonymChoiceDTO.serializer()

            ANTONYM_CHOICE_TYPE_NAME -> TaskDTO.AntonymChoiceDTO.serializer()

            AUDIO_MATCHING_TYPE_NAME -> TaskDTO.AudioMatchingDTO.serializer()

            LISTEN_SELECT_TRANSLATION_TYPE_NAME -> TaskDTO.ListenSelectTranslationDTO.serializer()

            IMAGE_SELECT_WORD_TRANSLATION_TYPE_NAME -> {
                TaskDTO.ImageSelectWordTranslationDTO.serializer()
            }

            else -> TaskDTO.UnknowQuestionDTO.serializer()
        }
    }
}
