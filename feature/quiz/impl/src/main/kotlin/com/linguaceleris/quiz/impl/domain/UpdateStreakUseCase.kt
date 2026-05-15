package com.linguaceleris.quiz.impl.domain

import com.linguaceleris.auth.AuthRepository
import com.linguaceleris.quiz.api.QuizLevel
import com.linguaceleris.streak.StreakRepository
import javax.inject.Inject

internal class UpdateStreakUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val streakRepository: StreakRepository,
) {

    suspend operator fun invoke(level: QuizLevel) {
        val id = authRepository.getCurrentUserId() ?: error("User id is null")
        when (level) {
            QuizLevel.BASIC -> streakRepository.updateBasicStreak(id)
            QuizLevel.INTERMEDIATE -> streakRepository.updateIntermediateStreak(id)
            QuizLevel.ADVANCED -> streakRepository.updateAdvancedStreak(id)
        }
    }
}
