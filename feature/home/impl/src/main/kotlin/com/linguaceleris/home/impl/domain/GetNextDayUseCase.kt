package com.linguaceleris.home.impl.domain

import com.linguaceleris.services.time.TrustedTimeManager
import javax.inject.Inject
import kotlin.time.Duration
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val TIMER_UPDATE = 1000L

internal class GetNextDayUseCase @Inject constructor(private val timeManager: TrustedTimeManager) {

    operator fun invoke(): Flow<Duration> = flow {
        while (true) {
            val duration = timeManager.getTimeTillMidnight()
            emit(duration)
            delay(TIMER_UPDATE)
        }
    }
}
