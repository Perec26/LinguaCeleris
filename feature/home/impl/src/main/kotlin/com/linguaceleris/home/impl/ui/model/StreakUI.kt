package com.linguaceleris.home.impl.ui.model

import androidx.annotation.StringRes
import com.linguaceleris.home.impl.R
import com.linguaceleris.home.impl.ui.model.StreakUI.TodayCompleted

internal sealed class StreakUI {
    data class Dead(val lastStreak: Int, val longestStreak: Int) : StreakUI()
    data class TodayCompleted(val streak: Int, @param:StringRes val subtitle: Int) : StreakUI()
    data class TodayNotCompleted(val streak: Int) : StreakUI()
    data object NeverStarted : StreakUI()
    sealed class Freeze(@param:StringRes val title: Int, @param:StringRes val subtitle: Int,) :
        StreakUI() {
        data object OneFreeze : Freeze(
            title = R.string.home_streak_one_freeze_title,
            subtitle = R.string.home_streak_one_freeze_subtitle,
        )

        data object NoneFreeze : Freeze(
            title = R.string.home_streak_none_freeze_title,
            subtitle = R.string.home_streak_none_freeze_subtitle,
        )
    }
}

internal fun createFreeze(freezeCount: Int): StreakUI.Freeze = if (freezeCount == 1) {
    StreakUI.Freeze.OneFreeze
} else {
    StreakUI.Freeze.NoneFreeze
}

internal fun createTodayCompleted(streak: Int, dateHash: Int): TodayCompleted {
    val subtitle = when (streak) {
        1 -> R.string.home_streak_completed_1
        2, 3 -> R.string.home_streak_completed_3
        7 -> R.string.home_streak_completed_7
        14 -> R.string.home_streak_completed_14
        30 -> R.string.home_streak_completed_30
        50 -> R.string.home_streak_completed_50
        100 -> R.string.home_streak_completed_100
        365 -> R.string.home_streak_completed_365
        else -> getRandomSubtitle(dateHash)
    }
    return TodayCompleted(streak, subtitle)
}

private fun getRandomSubtitle(dateHash: Int) = when (dateHash % 7) {
    0 -> R.string.home_streak_completed_any1
    1 -> R.string.home_streak_completed_any2
    2 -> R.string.home_streak_completed_any3
    3 -> R.string.home_streak_completed_any4
    4 -> R.string.home_streak_completed_any5
    5 -> R.string.home_streak_completed_any6
    6 -> R.string.home_streak_completed_any7
    else -> R.string.home_streak_completed_any7
}
