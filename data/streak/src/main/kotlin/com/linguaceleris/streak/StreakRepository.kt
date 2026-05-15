package com.linguaceleris.streak

import com.linguaceleris.services.time.TrustedTimeManager
import com.linguaceleris.streak.model.StreakDTO
import javax.inject.Inject
import kotlin.math.absoluteValue
import kotlinx.datetime.daysUntil

private const val MAX_DAYS = 3

class StreakRepository @Inject constructor(
    private val trustedTimeManager: TrustedTimeManager,
    private val dataSource: StreakDataSource,
) {

    suspend fun getStreak(userId: String) = dataSource.getStreak(userId) ?: StreakDTO()

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

    suspend fun updateBasicStreak(id: String) {
        getUpdatedStreak(id)
            .copy(basicLastCompletedDate = trustedTimeManager.getCurrentDate())
            .apply { dataSource.updateStreak(id, this) }
    }

    suspend fun updateIntermediateStreak(id: String) {
        getUpdatedStreak(id)
            .copy(intermediateLastCompletedDate = trustedTimeManager.getCurrentDate())
            .apply { dataSource.updateStreak(id, this) }
    }

    suspend fun updateAdvancedStreak(id: String) {
        getUpdatedStreak(id)
            .copy(advancedLastCompletedDate = trustedTimeManager.getCurrentDate())
            .apply { dataSource.updateStreak(id, this) }
    }
}
