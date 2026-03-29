package com.linguaceleris.auth.impl.ui.email

import com.linguaceleris.auth.impl.domain.GetEmailVerificationUseCase
import com.linguaceleris.auth.impl.domain.SignInWithEmailUseCase
import com.linguaceleris.auth.impl.domain.ValidateEmailUseCase
import com.linguaceleris.auth.impl.domain.ValidatePasswordUseCase
import com.linguaceleris.navigation.Navigator
import io.mockk.mockk

internal object EmailSignInMocks {
    val navigator = mockk<Navigator>(relaxed = true)
    val validateEmailUseCase = mockk<ValidateEmailUseCase>(relaxed = true)
    val validatePasswordUseCase = mockk<ValidatePasswordUseCase>(relaxed = true)
    val signInWithEmailUseCase = mockk<SignInWithEmailUseCase>(relaxed = true)
    val getEmailVerificationUseCase = mockk<GetEmailVerificationUseCase>(relaxed = true)
}
