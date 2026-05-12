package com.linguaceleris.quiz.impl.ui.quiz

import com.linguaceleris.media.PlayerManager
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quiz.impl.domain.GetTasksUseCase
import com.linguaceleris.quiz.impl.domain.SelectVariantUseCase
import com.linguaceleris.quiz.impl.ui.quiz.model.MatchingPairUI
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskTypeUI
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI
import com.linguaceleris.quiz.impl.ui.quiz.model.WordCardUI
import io.mockk.mockk

internal object QuizMocks {
    val navigator = mockk<Navigator>(relaxed = true)
    val getTasksUseCase = mockk<GetTasksUseCase>(relaxed = true)
    val playerManager = mockk<PlayerManager>(relaxed = true)
    val selectVariantUseCase = mockk<SelectVariantUseCase>(relaxed = true)
}

internal val correctAnswerMock = WordCardUI(audio = "audio", text = "cat")
internal val incorrectAnswerMock = WordCardUI(audio = "audio", text = "bat")

internal val variantsMock = listOf(
    correctAnswerMock,
    incorrectAnswerMock,
    WordCardUI(audio = "audio", text = "fat"),
    WordCardUI(audio = "audio", text = "rat"),
)

internal val taskSelectCorrectAnswerMock = TaskUI.SelectCorrectAnswer(
    question = WordCardUI(audio = "audio", text = "Кошка"),
    correctAnswer = correctAnswerMock,
    answerVariants = variantsMock,
    id = "id",
    type = TaskTypeUI.SelectTranslation,
)

internal val taskMatchingMock = TaskUI.Matching(
    pairs = variantsMock.zip(variantsMock) { first, second ->
        MatchingPairUI(first, second)
    },
    id = "id",
    type = TaskTypeUI.Matching,
)
