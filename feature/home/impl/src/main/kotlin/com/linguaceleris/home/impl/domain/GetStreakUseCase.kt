package com.linguaceleris.home.impl.domain

import com.linguaceleris.auth.AuthRepository
import com.linguaceleris.home.impl.domain.mapper.toUi
import com.linguaceleris.home.impl.domain.model.UserNotFoundException
import com.linguaceleris.home.impl.ui.model.StreakUI
import com.linguaceleris.services.time.TrustedTimeManager
import com.linguaceleris.streak.StreakRepository
import javax.inject.Inject

internal class GetStreakUseCase @Inject constructor(
    private val timeManager: TrustedTimeManager,
    private val authRepository: AuthRepository,
    private val streakRepository: StreakRepository,
) {
    suspend operator fun invoke(): StreakUI {
        val id = authRepository.getCurrentUserId() ?: throw UserNotFoundException()
        val currentDate = timeManager.getCurrentDate()
        return streakRepository.getStreak(id).toUi(currentDate)
    }
}
