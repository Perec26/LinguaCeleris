package com.linguaceleris.quiz.impl.ui.summary

import com.linguaceleris.media.PlayerManager
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quiz.impl.domain.GetUnfinishedQuizzesUseCase
import com.linguaceleris.quiz.impl.domain.UpdateStreakUseCase
import io.mockk.mockk

internal object QuizSummaryMocks {
    val navigator = mockk<Navigator>(relaxed = true)
    val updateStreakUseCase = mockk<UpdateStreakUseCase>(relaxed = true)
    val getUnfinishedQuizzesUseCase = mockk<GetUnfinishedQuizzesUseCase>(relaxed = true)
    val playerManager = mockk<PlayerManager>(relaxed = true)
}
