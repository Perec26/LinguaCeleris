package com.linguaceleris.auth.impl.ui.signin

import com.linguaceleris.auth.impl.domain.GetWebClientIdUseCase
import com.linguaceleris.auth.impl.domain.SignInAnonymouslyUseCase
import com.linguaceleris.auth.impl.domain.SignInWithGoogleUseCase
import com.linguaceleris.navigation.Navigator
import io.mockk.mockk

internal object SignInMocks {
    val navigator = mockk<Navigator>(relaxed = true)
    val signInWithGoogleUseCase = mockk<SignInWithGoogleUseCase>(relaxed = true)
    val getWebClientIdUseCase = mockk<GetWebClientIdUseCase>(relaxed = true)
    val signInAnonymouslyUseCase = mockk<SignInAnonymouslyUseCase>(relaxed = true)
}
