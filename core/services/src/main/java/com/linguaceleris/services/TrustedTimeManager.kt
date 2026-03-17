package com.linguaceleris.services

import kotlinx.coroutines.tasks.await
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class TrustedTimeManager @Inject constructor(
    private val trustedTimeClientAccessor: TrustedTimeClientAccessor,
) {

    // TODO: обработать нулабельность computeCurrentUnixEpochMillis()
    suspend fun getLocalDate(): LocalDate {
        val client = trustedTimeClientAccessor.createClient().await()
        val timeInMillis = client.computeCurrentUnixEpochMillis() ?: System.currentTimeMillis()
        val date = Instant
            .fromEpochMilliseconds(timeInMillis)
            .toLocalDateTime(TimeZone.UTC)
            .date
        client.dispose()
        return date
    }
}
