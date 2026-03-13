package com.linguaceleris.quiz.impl.domain.mapper

import com.linguaceleris.quiz.impl.R
import com.linguaceleris.quiz.impl.ui.model.MatchingPairUI
import com.linguaceleris.quiz.impl.ui.model.TaskUI
import com.linguaceleris.quiz.impl.ui.model.TaskUI.Matching
import com.linguaceleris.quiz.impl.ui.model.TaskUI.SelectCorrectAnswer
import com.linguaceleris.quiz.impl.ui.model.WordCardUI
import com.linguaceleris.quiz.model.MatchingPairDTO
import com.linguaceleris.quiz.model.TaskDTO
import com.linguaceleris.quiz.model.TaskDataDTO
import com.linguaceleris.quiz.model.VariantDTO

fun VariantDTO.toUi() = WordCardUI(
    audio = audio,
    text = text,
    image = image
)

fun MatchingPairDTO.toUi() = MatchingPairUI(
    original = left.toUi(),
    translation = right.toUi(),
)

@JvmName("toMatchingPairUi")
fun List<MatchingPairDTO>.toUi() = map(MatchingPairDTO::toUi)

@JvmName("toVariantUi")
fun List<VariantDTO>.toUi() = map(VariantDTO::toUi)

@JvmName("toTaskUi")
fun List<TaskDTO>.toUi() = mapNotNull(TaskDTO::toUi)

fun TaskDTO.toUi(): TaskUI? = when (this.data) {
    is TaskDataDTO.ChooseCorrectDTO -> {
        val data = this.data as TaskDataDTO.ChooseCorrectDTO
        SelectCorrectAnswer(
            text = getTaskText(),
            info = getTaskInfo(),
            question = data.question.toUi(),
            correctAnswer = data.answer.toUi(),
            answerVariants = data.options.toUi().shuffled(),
        )
    }

    is TaskDataDTO.MatchingDataDTO -> {
        val data = this.data as TaskDataDTO.MatchingDataDTO
        val pairsUi = data.pairs.toUi()
        Matching(
            text = getTaskText(),
            info = getTaskInfo(),
            pairs = pairsUi,
            originalVariants = pairsUi.map { it.original }.shuffled(),
            translationVariants = pairsUi.map { it.translation }.shuffled(),
        )
    }

    TaskDataDTO.UnknownDTO -> null
}

fun TaskDTO.getTaskText() = when (this) {
    is TaskDTO.AntonymChoiceDTO -> R.string.quiz_short_select_translation
    is TaskDTO.AudioMatchingDTO -> R.string.quiz_short_audio_matching
    is TaskDTO.FillInTheBlankDTO -> R.string.quiz_short_fill_in_blank
    is TaskDTO.FindCorrectDTO -> R.string.quiz_short_find_correct
    is TaskDTO.HomophonesDTO -> R.string.quiz_short_homophones
    is TaskDTO.ImageSelectWordTranslationDTO -> R.string.quiz_short_image_select_word
    is TaskDTO.ListenSelectTranslationDTO -> R.string.quiz_short_listen_select_translation
    is TaskDTO.MatchingDTO -> R.string.quiz_short_matching
    is TaskDTO.SelectTranslationDTO -> R.string.quiz_short_select_translation
    is TaskDTO.SynonymChoiceDTO -> R.string.quiz_short_synonym_choice
    is TaskDTO.UnknowQuestionDTO -> R.string.quiz_short_unknown
}

fun TaskDTO.getTaskInfo() = when (this) {
    is TaskDTO.AntonymChoiceDTO -> R.string.quiz_long_select_translation
    is TaskDTO.AudioMatchingDTO -> R.string.quiz_long_audio_matching
    is TaskDTO.FillInTheBlankDTO -> R.string.quiz_long_fill_in_blank
    is TaskDTO.FindCorrectDTO -> R.string.quiz_long_find_correct
    is TaskDTO.HomophonesDTO -> R.string.quiz_long_homophones
    is TaskDTO.ImageSelectWordTranslationDTO -> R.string.quiz_long_image_select_word
    is TaskDTO.ListenSelectTranslationDTO -> R.string.quiz_long_listen_select_translation
    is TaskDTO.MatchingDTO -> R.string.quiz_long_matching
    is TaskDTO.SelectTranslationDTO -> R.string.quiz_long_select_translation
    is TaskDTO.SynonymChoiceDTO -> R.string.quiz_long_synonym_choice
    is TaskDTO.UnknowQuestionDTO -> R.string.quiz_long_unknown
}
