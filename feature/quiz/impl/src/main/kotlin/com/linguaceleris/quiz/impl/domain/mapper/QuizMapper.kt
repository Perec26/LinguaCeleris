package com.linguaceleris.quiz.impl.domain.mapper

import com.linguaceleris.quiz.impl.ui.quiz.model.MatchingPairUI
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskTypeUI
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI.Matching
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI.SelectCorrectAnswer
import com.linguaceleris.quiz.impl.ui.quiz.model.WordCardUI
import com.linguaceleris.quiz.model.MatchingPairDTO
import com.linguaceleris.quiz.model.TaskDTO
import com.linguaceleris.quiz.model.TaskDataDTO
import com.linguaceleris.quiz.model.VariantDTO

internal fun VariantDTO.toUi() = WordCardUI(
    audio = audio,
    text = text,
    image = image
)

internal fun MatchingPairDTO.toUi() = MatchingPairUI(
    original = left.toUi(),
    translation = right.toUi(),
)

@JvmName("toMatchingPairUi")
internal fun List<MatchingPairDTO>.toUi() = map(MatchingPairDTO::toUi)

@JvmName("toVariantUi")
internal fun List<VariantDTO>.toUi() = map(VariantDTO::toUi)

@JvmName("toTaskUi")
internal fun List<TaskDTO>.toUi() = mapNotNull(TaskDTO::toUi)

internal fun TaskDTO.toUi(): TaskUI? = when (this.data) {
    is TaskDataDTO.ChooseCorrectDTO -> {
        val data = this.data as TaskDataDTO.ChooseCorrectDTO
        SelectCorrectAnswer(
            type = getTaskType(),
            question = data.question.toUi(),
            correctAnswer = data.answer.toUi(),
            answerVariants = data.options.toUi().shuffled(),
        )
    }

    is TaskDataDTO.MatchingDataDTO -> {
        val data = this.data as TaskDataDTO.MatchingDataDTO
        val pairsUi = data.pairs.toUi()
        Matching(
            type = getTaskType(),
            pairs = pairsUi,
            originalVariants = pairsUi.map { it.original }.shuffled(),
            translationVariants = pairsUi.map { it.translation }.shuffled(),
        )
    }

    TaskDataDTO.UnknownDTO -> null
}

private fun TaskDTO.getTaskType() = when (this) {
    is TaskDTO.AntonymChoiceDTO -> TaskTypeUI.AntonymChoice
    is TaskDTO.AudioMatchingDTO -> TaskTypeUI.AudioMatching
    is TaskDTO.FillInTheBlankDTO -> TaskTypeUI.FillInBlank
    is TaskDTO.FindCorrectDTO -> TaskTypeUI.FindCorrect
    is TaskDTO.HomophonesDTO -> TaskTypeUI.Homophones
    is TaskDTO.ImageSelectWordTranslationDTO -> TaskTypeUI.ImageSelectWordTranslation
    is TaskDTO.ListenSelectTranslationDTO -> TaskTypeUI.ListenSelectTranslation
    is TaskDTO.MatchingDTO -> TaskTypeUI.Matching
    is TaskDTO.SelectTranslationDTO -> TaskTypeUI.SelectTranslationEn
    is TaskDTO.SynonymChoiceDTO -> TaskTypeUI.SynonymChoice
    is TaskDTO.UnknowQuestionDTO -> TaskTypeUI.Unknown
}
