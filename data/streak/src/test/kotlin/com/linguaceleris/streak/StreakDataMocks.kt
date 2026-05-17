package com.linguaceleris.streak

import com.linguaceleris.services.time.TrustedTimeManager
import io.mockk.mockk
import kotlinx.datetime.LocalDate

internal object StreakDataMocks {
    val timeManager = mockk<TrustedTimeManager>()
    val dataSource = mockk<StreakDataSource>()

    val today = LocalDate(2024, 5, 16)
    val yesterday = LocalDate(2024, 5, 15)
    val threeDaysAgo = LocalDate(2024, 5, 13)
    val fourDaysAgo = LocalDate(2024, 5, 12)
}
