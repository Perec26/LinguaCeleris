package com.linguaceleris.streak.model

import android.annotation.SuppressLint
import com.linguaceleris.network.serializer.LocalDateSerializer
import com.linguaceleris.time.findLatestDate
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class StreakDTO(
    val current: Int = 0,
    val longest: Int = 0,
    @Serializable(with = LocalDateSerializer::class)
    val basicLastCompletedDate: LocalDate? = null,
    @Serializable(with = LocalDateSerializer::class)
    val intermediateLastCompletedDate: LocalDate? = null,
    @Serializable(with = LocalDateSerializer::class)
    val advancedLastCompletedDate: LocalDate? = null,
) {

    val lastCompletedDate: LocalDate? = findLatestDate(
        basicLastCompletedDate,
        intermediateLastCompletedDate,
        advancedLastCompletedDate,
    )
}
