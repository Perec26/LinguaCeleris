package com.linguaceleris.quiz.impl.domain.mapper

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
            question = data.question.toUi(),
            correctAnswer = data.answer.toUi(),
            answerVariants = data.options.toUi(),
        )
    }

    is TaskDataDTO.MatchingDataDTO -> {
        val data = this.data as TaskDataDTO.MatchingDataDTO
        val pairsUi = data.pairs.toUi()
        Matching(
            pairs = pairsUi,
            originalVariants = pairsUi.map { it.original }.shuffled(),
            translationVariants = pairsUi.map { it.translation }.shuffled(),
        )
    }

    TaskDataDTO.UnknownDTO -> null
}
