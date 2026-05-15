package com.linguaceleris.start.impl

import com.linguaceleris.navigation.Navigator
import com.linguaceleris.start.impl.domain.GetAuthStateUseCase
import io.mockk.mockk

internal object StartMocks {
    val navigator = mockk<Navigator>(relaxed = true)
    val getAuthStateUseCase = mockk<GetAuthStateUseCase>(relaxed = true)
}
