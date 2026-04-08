package com.linguaceleris.quiz.impl.domain

import com.linguaceleris.auth.AuthRepository
import com.linguaceleris.quiz.api.QuizDifficulty
import com.linguaceleris.quiz.impl.domain.mapper.toStreakDTO
import com.linguaceleris.streak.StreakRepository
import javax.inject.Inject

internal class UpdateStreakUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val streakRepository: StreakRepository,
) {

    suspend operator fun invoke(difficulty: QuizDifficulty) {
        val id = authRepository.getCurrentUserId() ?: error("User id is null")
        streakRepository.updateStreak(id, difficulty.toStreakDTO())
    }
}
