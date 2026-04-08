package com.linguaceleris.streak

import com.linguaceleris.services.time.TrustedTimeManager
import com.linguaceleris.streak.model.StreakDTO
import com.linguaceleris.streak.model.StreakDifficultyDTO
import javax.inject.Inject
import kotlin.math.absoluteValue
import kotlinx.datetime.daysUntil

private const val MAX_DAYS = 3

class StreakRepository @Inject constructor(
    private val trustedTimeManager: TrustedTimeManager,
    private val dataSource: StreakDataSource,
) {

    suspend fun getStreak(userId: String) = dataSource.getStreak(userId) ?: StreakDTO()

    suspend fun updateStreak(id: String, difficulty: StreakDifficultyDTO) {
        val currentDate = trustedTimeManager.getCurrentDate()
        val currentStreak = getUpdatedStreak(id)

        val newStreak = when (difficulty) {
            StreakDifficultyDTO.BASIC -> currentStreak.copy(basicLastCompletedDate = currentDate)

            StreakDifficultyDTO.INTERMEDIATE -> currentStreak.copy(
                intermediateLastCompletedDate = currentDate,
            )

            StreakDifficultyDTO.ADVANCED -> currentStreak.copy(
                advancedLastCompletedDate = currentDate,
            )
        }
        dataSource.updateStreak(id, newStreak)
    }

    private suspend fun getUpdatedStreak(userId: String): StreakDTO {
        val streak = dataSource.getStreak(userId) ?: StreakDTO()
        val currentDate = trustedTimeManager.getCurrentDate()

        val daysUntil = currentDate.daysUntil(streak.lastCompletedDate ?: currentDate).absoluteValue

        return when {
            streak.longest == 0 -> streak.copy(current = 1, longest = 1)

            daysUntil > MAX_DAYS -> streak.copy(current = 1)

            daysUntil == 0 -> streak.copy()

            else -> streak.copy(
                current = streak.current + 1,
                longest = maxOf(streak.current + 1, streak.longest),
            )
        }
    }
}
