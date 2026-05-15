package com.linguaceleris.auth.impl.ui.linkaccount

import com.linguaceleris.auth.impl.domain.GetWebClientIdUseCase
import com.linguaceleris.auth.impl.domain.LinkWithGoogleUseCase
import com.linguaceleris.navigation.Navigator
import io.mockk.mockk

internal object LinkAccountMocks {
    val navigator = mockk<Navigator>(relaxed = true)
    val getWebClientIdUseCase = mockk<GetWebClientIdUseCase>(relaxed = true)
    val linkWithGoogleUseCase = mockk<LinkWithGoogleUseCase>(relaxed = true)
}
