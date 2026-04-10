package com.linguaceleris.home.impl.domain.mapper

import com.linguaceleris.home.impl.ui.model.QuizCompletionUI
import com.linguaceleris.home.impl.ui.model.StreakUI
import com.linguaceleris.home.impl.ui.model.createFreeze
import com.linguaceleris.home.impl.ui.model.createTodayCompleted
import com.linguaceleris.streak.model.StreakDTO
import com.linguaceleris.time.findLatestDate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil

private const val MAX_DAYS_UNTIL = 3

internal fun StreakDTO.toUi(currentDate: LocalDate): StreakUI {
    val lastCompletedDate = findLatestDate(
        basicLastCompletedDate,
        intermediateLastCompletedDate,
        advancedLastCompletedDate,
    ) ?: currentDate

    val completion = QuizCompletionUI(
        basicIsCompleted = basicLastCompletedDate == currentDate,
        intermediateIsCompleted = intermediateLastCompletedDate == currentDate,
        advancedIsCompleted = advancedLastCompletedDate == currentDate,
    )

    val daysWithoutCompleted = lastCompletedDate.daysUntil(currentDate)

    return when {
        longest == 0 -> StreakUI.NeverStarted

        daysWithoutCompleted == 0 -> createTodayCompleted(
            streak = current,
            completion = completion,
            dateHash = lastCompletedDate.hashCode(),
        )

        daysWithoutCompleted == 1 -> StreakUI.TodayNotCompleted(current)

        daysWithoutCompleted <= MAX_DAYS_UNTIL -> createFreeze(
            freezeCount = MAX_DAYS_UNTIL - daysWithoutCompleted,
        )

        else -> StreakUI.Dead(longestStreak = longest)
    }
}
