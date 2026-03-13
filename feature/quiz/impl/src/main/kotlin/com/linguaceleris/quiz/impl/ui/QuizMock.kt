package com.linguaceleris.quiz.impl.ui

import com.linguaceleris.quiz.impl.R
import com.linguaceleris.quiz.impl.ui.model.MatchingPairUI
import com.linguaceleris.quiz.impl.ui.model.TaskUI
import com.linguaceleris.quiz.impl.ui.model.WordCardUI

internal val fourEnVariantsMock = listOf(
    WordCardUI(audio = "audio", text = "cat"),
    WordCardUI(audio = "audio", text = "bat"),
    WordCardUI(audio = "audio", text = "fat"),
    WordCardUI(audio = "audio", text = "illustration"),
)

internal val fiveRuVariantsMock = listOf(
    WordCardUI(audio = "audio", text = "кошка"),
    WordCardUI(audio = "audio", text = "толстый"),
    WordCardUI(audio = "audio", text = "иллюстрация"),
    WordCardUI(audio = "audio", text = "перегородка"),
    WordCardUI(audio = "audio", text = "летучая мышь"),
)

internal val matchingMock = TaskUI.Matching(
    pairs = fourEnVariantsMock.zip(fiveRuVariantsMock.dropLast(1)) { first, second ->
        MatchingPairUI(first, second)
    },
    selectedVariant = WordCardUI(audio = "audio", text = "bat"),
    text = R.string.quiz_short_matching,
    info = R.string.quiz_long_matching,
)

internal val selectCorrectAnswerMock = TaskUI.SelectCorrectAnswer(
    question = WordCardUI(audio = "audio", text = "кошка"),
    correctAnswer = WordCardUI(audio = "audio", text = "cat"),
    selectedVariant = WordCardUI(audio = "audio", text = "bat"),
    answerVariants = fourEnVariantsMock,
    text = R.string.quiz_short_select_translation,
    info = R.string.quiz_long_select_translation,
)

internal val selectCorrectAnswerImageMock = selectCorrectAnswerMock.copy(
    question = WordCardUI(audio = "audio", text = "кошка", image = "image"),
)

internal val quizStateMock = QuizUiState(
    tasks = listOf(selectCorrectAnswerMock),
    screenState = ScreenState.CONTENT,
    currentTaskIndex = 0,
    currentTask = selectCorrectAnswerMock,
)
