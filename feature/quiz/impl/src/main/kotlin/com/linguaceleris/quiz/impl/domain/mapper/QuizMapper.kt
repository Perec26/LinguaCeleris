package com.linguaceleris.quiz.impl.domain.mapper

import com.linguaceleris.lib.ProgressWrapper
import com.linguaceleris.quiz.impl.ui.quiz.model.MatchingPairUI
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskTypeUI
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI.Matching
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI.SelectCorrectAnswer
import com.linguaceleris.quiz.impl.ui.quiz.model.WordCardUI
import com.linguaceleris.quiz.model.MatchingPairDTO
import com.linguaceleris.quiz.model.QuizDTO
import com.linguaceleris.quiz.model.TaskDTO
import com.linguaceleris.quiz.model.TaskDataDTO
import com.linguaceleris.quiz.model.VariantDTO

private const val FIND_CORRECT_REPLACEMENT = "_____"
private const val TASK_REQUIRED_NUMBER = 12

internal fun VariantDTO.toUi(replace: String? = null): WordCardUI {
    val newText = replace?.let { text.replace(it, FIND_CORRECT_REPLACEMENT) } ?: text
    return WordCardUI(
        audio = audio,
        text = newText,
        image = image,
    )
}

internal fun MatchingPairDTO.toUi() = left.toUi() to right.toUi()

@JvmName("toMatchingPairUi")
internal fun List<MatchingPairDTO>.toUi() = map(MatchingPairDTO::toUi)

@JvmName("toVariantUi")
internal fun List<VariantDTO>.toUi() = map(VariantDTO::toUi)

@JvmName("toTaskUi")
internal fun List<TaskDTO>.toUi() = mapNotNull(TaskDTO::toUi)

internal fun ProgressWrapper<QuizDTO>.toUi(): ProgressWrapper<List<TaskUI>> {
    return when (this) {
        is ProgressWrapper.Failure -> ProgressWrapper.Failure(error)

        is ProgressWrapper.Loading -> ProgressWrapper.Loading(progress)

        is ProgressWrapper.Success -> {
            val tasks = this.value.tasks.toUi()
            if (tasks.size < TASK_REQUIRED_NUMBER) {
                val extraPoolSize = TASK_REQUIRED_NUMBER - tasks.size
                val extraPool = value.extraPool.shuffled().take(extraPoolSize).toUi()
                return ProgressWrapper.Success((tasks + extraPool).shuffled())
            }
            ProgressWrapper.Success(tasks.shuffled())
        }
    }
}

internal fun TaskDTO.toUi(): TaskUI? = when (val data = this.data) {
    is TaskDataDTO.ChooseCorrectDTO -> {
        val type = getTaskType()
        val replacement = if (type is TaskTypeUI.FillInBlank) data.answer.text else null
        SelectCorrectAnswer(
            id = id,
            type = type,
            question = data.question.toUi(replacement),
            correctAnswer = data.answer.toUi(),
            answerVariants = data.options.toUi().shuffled(),
        )
    }

    is TaskDataDTO.MatchingDataDTO -> {
        val pairsUi = data.pairs.toUi()
        Matching(
            id = id,
            type = getTaskType(),
            pairs = pairsUi,
            originalVariants = pairsUi.map(MatchingPairUI::first).shuffled(),
            translationVariants = pairsUi.map(MatchingPairUI::second).shuffled(),
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
    is TaskDTO.SelectAudioDTO -> TaskTypeUI.SelectAudio
    is TaskDTO.SelectTranslationDTO -> TaskTypeUI.SelectTranslation
    is TaskDTO.SynonymChoiceDTO -> TaskTypeUI.SynonymChoice
    is TaskDTO.UnknowQuestionDTO -> TaskTypeUI.Unknown
}
