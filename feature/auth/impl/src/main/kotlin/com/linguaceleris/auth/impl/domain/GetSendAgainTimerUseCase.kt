package com.linguaceleris.auth.impl.domain

import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val SEND_AGAIN_TIMER = 60

internal class GetSendAgainTimerUseCase @Inject constructor() {

    operator fun invoke(): Flow<Int> = flow {
        var timerValue = SEND_AGAIN_TIMER
        emit(timerValue)
        while (timerValue > 0) {
            delay(1000)
            timerValue -= 1
            emit(timerValue)
        }
    }
}
