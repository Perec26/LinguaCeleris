package com.linguaceleris.auth.impl.ui.registration

import com.linguaceleris.auth.impl.domain.GetEmailVerificationUseCase
import com.linguaceleris.auth.impl.domain.GetSendAgainTimerUseCase
import com.linguaceleris.auth.impl.domain.RegisterUseCase
import com.linguaceleris.auth.impl.domain.SendEmailVerificationUseCase
import com.linguaceleris.auth.impl.domain.ValidateConfirmPasswordUseCase
import com.linguaceleris.auth.impl.domain.ValidateEmailUseCase
import com.linguaceleris.auth.impl.domain.ValidateNicknameUseCase
import com.linguaceleris.auth.impl.domain.ValidatePasswordUseCase
import com.linguaceleris.navigation.Navigator
import io.mockk.mockk

internal object RegistrationMocks {
    val navigator = mockk<Navigator>(relaxed = true)
    val validateNicknameUseCase = mockk<ValidateNicknameUseCase>(relaxed = true)
    val validateEmailUseCase = mockk<ValidateEmailUseCase>(relaxed = true)
    val validatePasswordUseCase = mockk<ValidatePasswordUseCase>(relaxed = true)
    val validateConfirmPasswordUseCase = mockk<ValidateConfirmPasswordUseCase>(relaxed = true)
    val registerUseCase = mockk<RegisterUseCase>(relaxed = true)
    val sendEmailVerificationUseCase = mockk<SendEmailVerificationUseCase>(relaxed = true)
    val getEmailVerificationUseCase = mockk<GetEmailVerificationUseCase>(relaxed = true)
    val getSendAgainTimerUseCase = mockk<GetSendAgainTimerUseCase>(relaxed = true)
}
