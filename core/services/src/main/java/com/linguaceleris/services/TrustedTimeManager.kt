package com.linguaceleris.services

import javax.inject.Inject
import kotlin.time.Instant
import kotlinx.coroutines.tasks.await
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

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
