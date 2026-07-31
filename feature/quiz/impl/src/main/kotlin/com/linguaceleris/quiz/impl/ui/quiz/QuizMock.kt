@file:Suppress("unused")

package com.linguaceleris.quiz.impl.ui.quiz

import com.linguaceleris.quiz.impl.ui.quiz.model.MatchingPairUI
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskTypeUI
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI
import com.linguaceleris.quiz.impl.ui.quiz.model.WordCardUI
import com.linguaceleris.ui.ScreenState

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
    pairs = fourEnVariantsMock.zip(fourEnVariantsMock) { first, second ->
        MatchingPairUI(first, second)
    },
    selectedVariant = WordCardUI(audio = "audio", text = "bat"),
    id = "",
    type = TaskTypeUI.Matching,
)

internal val selectCorrectAnswerMock = TaskUI.SelectCorrectAnswer(
    question = WordCardUI(audio = "audio", text = "Кошка"),
    correctAnswer = WordCardUI(audio = "audio", text = "cat"),
    selectedVariant = WordCardUI(audio = "audio", text = "bat"),
    answerVariants = fourEnVariantsMock,
    id = "",
    type = TaskTypeUI.SelectTranslation,
)

internal val selectCorrectAnswerFillInBlankMock = TaskUI.SelectCorrectAnswer(
    question = WordCardUI(
        text = "Very long example with a lot of words that can't fit to a single line of text",
    ),
    correctAnswer = WordCardUI(audio = "words", text = "cat"),
    selectedVariant = WordCardUI(audio = "audio", text = "bat"),
    answerVariants = fourEnVariantsMock,
    id = "",
    type = TaskTypeUI.FillInBlank,
)

internal val selectCorrectAnswerInThreeMock = TaskUI.SelectCorrectAnswer(
    question = WordCardUI(audio = "audio", text = "Кошка"),
    correctAnswer = WordCardUI(audio = "audio", text = "cat"),
    selectedVariant = WordCardUI(audio = "audio", text = "bat"),
    answerVariants = fourEnVariantsMock.takeLast(3),
    id = "",
    type = TaskTypeUI.SelectTranslation,
)

internal val fillInBlankMock = selectCorrectAnswerMock.copy(
    question = WordCardUI(audio = "audio", text = "I have a cat"),
    type = TaskTypeUI.FillInBlank,
)

internal val listenSelectTranslationMock = selectCorrectAnswerMock.copy(
    question = WordCardUI(audio = "audio", text = "I have a cat"),
    type = TaskTypeUI.ListenSelectTranslation,
)

internal val selectAudioMock = TaskUI.SelectCorrectAnswer(
    question = WordCardUI(audio = "audio", text = "Кошка"),
    correctAnswer = WordCardUI(audio = "audio", text = "cat"),
    selectedVariant = WordCardUI(audio = "audio", text = "bat"),
    answerVariants = fourEnVariantsMock,
    id = "",
    type = TaskTypeUI.SelectAudio,
)

internal val listenMock = TaskUI.SelectCorrectAnswer(
    question = WordCardUI(audio = "audio", text = "Кошка"),
    correctAnswer = WordCardUI(audio = "audio", text = "cat"),
    selectedVariant = WordCardUI(audio = "audio", text = "bat"),
    answerVariants = fourEnVariantsMock,
    id = "",
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
