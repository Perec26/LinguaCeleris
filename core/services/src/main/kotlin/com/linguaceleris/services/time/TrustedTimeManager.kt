package com.linguaceleris.services.time

import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Instant
import kotlinx.coroutines.tasks.await
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class TrustedTimeManager @Inject constructor(
    private val trustedTimeClientAccessor: TrustedTimeClientAccessor,
) {

    // TODO: обработать нулабельность computeCurrentUnixEpochMillis()
    suspend fun getCurrentDateTime(): LocalDateTime {
        val client = trustedTimeClientAccessor.createClient().await()
        val timeInMillis = client.computeCurrentUnixEpochMillis() ?: System.currentTimeMillis()
        val date = Instant
            .fromEpochMilliseconds(timeInMillis)
            .toLocalDateTime(TimeZone.UTC)
        client.dispose()
        return date
    }

    suspend fun getCurrentDate() = getCurrentDateTime().date

    suspend fun getTimeTillMidnight(): Duration {
        val now = getCurrentDateTime()
        val nextMidnight = now.date
            .plus(1, DateTimeUnit.DAY)
            .atStartOfDayIn(TimeZone.UTC)

        val newNow = now.toInstant(TimeZone.UTC)
        return nextMidnight - newNow
    }
}
