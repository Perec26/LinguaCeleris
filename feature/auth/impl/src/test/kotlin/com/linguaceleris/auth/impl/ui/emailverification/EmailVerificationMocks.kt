package com.linguaceleris.auth.impl.ui.emailverification

import com.linguaceleris.auth.impl.domain.GetEmailVerificationUseCase
import com.linguaceleris.auth.impl.domain.GetSendAgainTimerUseCase
import com.linguaceleris.auth.impl.domain.SendEmailVerificationUseCase
import com.linguaceleris.auth.impl.domain.SignOutUseCase
import com.linguaceleris.navigation.Navigator
import io.mockk.mockk

internal object EmailVerificationMocks {
    val navigator = mockk<Navigator>(relaxed = true)
    val getEmailVerificationUseCase = mockk<GetEmailVerificationUseCase>(relaxed = true)
    val signOutUseCase = mockk<SignOutUseCase>(relaxed = true)
    val sendEmailVerificationUseCase = mockk<SendEmailVerificationUseCase>(relaxed = true)
    val getSendAgainTimerUseCase = mockk<GetSendAgainTimerUseCase>(relaxed = true)
}
