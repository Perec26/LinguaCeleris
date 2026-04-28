package com.linguaceleris.home.impl.domain

import com.linguaceleris.auth.AuthRepository
import com.linguaceleris.services.time.TrustedTimeManager
import com.linguaceleris.streak.StreakRepository
import io.mockk.mockk

internal object HomeDomainMocks {
    val timeManager = mockk<TrustedTimeManager>()
    val authRepository = mockk<AuthRepository>()
    val streakRepository = mockk<StreakRepository>()
}
