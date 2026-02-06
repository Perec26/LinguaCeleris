package com.linguaceleris.quiz.model

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

private const val SELECT_TRANSLATION_EN_TYPE_NAME = "select_translation_en"
private const val SELECT_TRANSLATION_RU_TYPE_NAME = "select_translation_ru"
private const val MATCHING_TYPE_NAME = "matching"
private const val HOMOPHONES_TYPE_NAME = "homophones"
private const val FIND_CORRECT_TYPE_NAME = "find_correct"

@Serializable(with = QuestionSerializer::class)
sealed class QuestionDTO {
    abstract val id: String
    abstract val type: String

    @Serializable
    data class SelectTranslationEnDTO(
        override val id: String,
        override val type: String = SELECT_TRANSLATION_EN_TYPE_NAME,
        val data: SelectTranslationData,
    ) : QuestionDTO()

    @Serializable
    data class SelectTranslationRuDTO(
        override val id: String,
        override val type: String = SELECT_TRANSLATION_RU_TYPE_NAME,
        val data: SelectTranslationData,
    ) : QuestionDTO()

    @Serializable
    data class MatchingDTO(
        override val id: String,
        override val type: String = MATCHING_TYPE_NAME,
        val data: MatchingData,
    ) : QuestionDTO()

    @Serializable
    data class HomophonesDTO(
        override val id: String,
        override val type: String = HOMOPHONES_TYPE_NAME,
        val data: SelectTranslationData,
    ) : QuestionDTO()

    @Serializable
    data class FindCorrectDTO(
        override val id: String,
        override val type: String = FIND_CORRECT_TYPE_NAME,
        val data: SelectTranslationData,
    ) : QuestionDTO()

    @Serializable
    data class UnknowQuestionDTO(
        override val id: String,
        override val type: String,
    ) : QuestionDTO()
}

private object QuestionSerializer :
    JsonContentPolymorphicSerializer<QuestionDTO>(QuestionDTO::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<QuestionDTO> {
        val type = element.jsonObject.getValue("type").jsonPrimitive.content
        return when (type) {
            SELECT_TRANSLATION_EN_TYPE_NAME -> QuestionDTO.SelectTranslationEnDTO.serializer()
            SELECT_TRANSLATION_RU_TYPE_NAME -> QuestionDTO.SelectTranslationRuDTO.serializer()
            MATCHING_TYPE_NAME -> QuestionDTO.MatchingDTO.serializer()
            HOMOPHONES_TYPE_NAME -> QuestionDTO.HomophonesDTO.serializer()
            FIND_CORRECT_TYPE_NAME -> QuestionDTO.FindCorrectDTO.serializer()
            else -> QuestionDTO.UnknowQuestionDTO.serializer()
        }
    }
}
