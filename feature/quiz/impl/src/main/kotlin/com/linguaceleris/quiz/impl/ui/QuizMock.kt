package com.linguaceleris.quiz.impl.ui

import com.linguaceleris.quiz.impl.ui.model.MatchingPairUI
import com.linguaceleris.quiz.impl.ui.model.TaskUI
import com.linguaceleris.quiz.impl.ui.model.WordCardUI

internal val fourEnVariantsMock = listOf(
    WordCardUI("audio", "cat"),
    WordCardUI("audio", "bat"),
    WordCardUI("audio", "fat"),
    WordCardUI("audio", "illustration"),
)

internal val fiveRuVariantsMock = listOf(
    WordCardUI("audio", "кошка"),
    WordCardUI("audio", "толстый"),
    WordCardUI("audio", "иллюстрация"),
    WordCardUI("audio", "перегородка"),
    WordCardUI("audio", "летучая мышь"),
)

internal val matchingMock = TaskUI.Matching(
    pairs = fourEnVariantsMock.zip(fiveRuVariantsMock.dropLast(1)) { first, second ->
        MatchingPairUI(first, second)
    },
    selectedVariant = WordCardUI("audio", "bat"),
)

internal val selectCorrectAnswerMock = TaskUI.SelectCorrectAnswer(
    question = WordCardUI("audio", "кошка"),
    correctAnswer = WordCardUI("audio", "cat"),
    selectedVariant = WordCardUI("audio", "bat"),
    answerVariants = fourEnVariantsMock,
)

internal val quizStateMock = QuizUiState(
    tasks = listOf(selectCorrectAnswerMock),
    screenState = ScreenState.CONTENT,
    currentTaskIndex = 0,
    currentTask = selectCorrectAnswerMock,
)
