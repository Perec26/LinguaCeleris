package com.linguaceleris.quiz.impl.ui

import com.linguaceleris.quiz.impl.ui.model.MatchingPairUI
import com.linguaceleris.quiz.impl.ui.model.TaskTypeUI
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
    type = TaskTypeUI.Matching,
)

internal val selectCorrectAnswerMock = TaskUI.SelectCorrectAnswer(
    question = WordCardUI(audio = "audio", text = "Кошка"),
    correctAnswer = WordCardUI(audio = "audio", text = "cat"),
    selectedVariant = WordCardUI(audio = "audio", text = "bat"),
    answerVariants = fourEnVariantsMock,
    type = TaskTypeUI.SelectTranslationEn,
)

internal val fillInBlankMock = selectCorrectAnswerMock.copy(
    question = WordCardUI(audio = "audio", text = "I have a cat"),
    type = TaskTypeUI.FillInBlank,
)

internal val listenSelectTranslationMock = selectCorrectAnswerMock.copy(
    question = WordCardUI(audio = "audio", text = "I have a cat"),
    type = TaskTypeUI.ListenSelectTranslation,
)

internal val imageSelectWordTranslation = selectCorrectAnswerMock.copy(
    question = WordCardUI(audio = "audio", text = "кошка", image = "image"),
    type = TaskTypeUI.ImageSelectWordTranslation,
)
internal val quizStateMock = QuizUiState(
    tasks = listOf(
        selectCorrectAnswerMock,
        matchingMock,
        fillInBlankMock,
        listenSelectTranslationMock,
        imageSelectWordTranslation,
    ),
    screenState = ScreenState.CONTENT,
    currentTaskIndex = 3,
    currentTask = selectCorrectAnswerMock,
)
