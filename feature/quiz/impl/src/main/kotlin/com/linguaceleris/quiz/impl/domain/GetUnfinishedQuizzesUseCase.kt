package com.linguaceleris.quiz.impl.domain

import com.linguaceleris.auth.AuthRepository
import com.linguaceleris.quiz.api.QuizLevel
import com.linguaceleris.services.time.TrustedTimeManager
import com.linguaceleris.streak.StreakRepository
import javax.inject.Inject
import kotlinx.datetime.LocalDate

internal class GetUnfinishedQuizzesUseCase @Inject constructor(
    private val streakRepository: StreakRepository,
    private val authRepository: AuthRepository,
    private val timeManager: TrustedTimeManager,
) {
    suspend operator fun invoke(): List<QuizLevel> {
        val id = authRepository.getCurrentUserId() ?: error("User id is null")
        val streak = streakRepository.getStreak(id)
        val currentDate = timeManager.getCurrentDate()
        return buildList {
            if (!streak.basicLastCompletedDate.isFinished(currentDate)) add(QuizLevel.BASIC)
            if (!streak.intermediateLastCompletedDate.isFinished(currentDate)) {
                add(QuizLevel.INTERMEDIATE)
            }
            if (!streak.advancedLastCompletedDate.isFinished(currentDate)) add(QuizLevel.ADVANCED)
        }
    }

    private fun LocalDate?.isFinished(currentDate: LocalDate): Boolean {
        if (this == null) return false
        return this >= currentDate
    }
}
