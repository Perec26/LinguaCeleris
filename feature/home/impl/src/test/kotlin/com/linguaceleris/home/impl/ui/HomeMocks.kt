package com.linguaceleris.home.impl.ui

import com.linguaceleris.home.impl.domain.GetNextDayUseCase
import com.linguaceleris.home.impl.domain.GetStreakUseCase
import com.linguaceleris.home.impl.domain.IsGuestUseCase
import com.linguaceleris.home.impl.domain.LoadScheduleUseCase
import com.linguaceleris.home.impl.domain.SignOutUseCase
import com.linguaceleris.home.impl.ui.model.StreakUI
import com.linguaceleris.navigation.Navigator
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow

internal object HomeMocks {
    val navigator = mockk<Navigator>(relaxed = true)
    val loadScheduleUseCase = mockk<LoadScheduleUseCase>(relaxed = true)
    val signOutUseCase = mockk<SignOutUseCase>(relaxed = true)
    val getStreakUseCase = mockk<GetStreakUseCase>(relaxed = true)
    val getNextDayUseCase = mockk<GetNextDayUseCase>(relaxed = true)
    val isGuestUseCase = mockk<IsGuestUseCase>(relaxed = true)

    fun setupDefaultMocks() {
        every { getNextDayUseCase() } returns emptyFlow()
        every { navigator.results } returns MutableSharedFlow()
        coEvery { getStreakUseCase() } returns StreakUI.NeverStarted
    }
}
