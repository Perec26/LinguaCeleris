package com.linguaceleris.auth.impl.ui.forgotpassword

import com.linguaceleris.auth.impl.domain.ResetPasswordUseCase
import com.linguaceleris.auth.impl.domain.ValidateEmailUseCase
import com.linguaceleris.navigation.Navigator
import io.mockk.mockk

internal object ForgotPasswordMocks {
    val navigator = mockk<Navigator>(relaxed = true)
    val resetPasswordUseCase = mockk<ResetPasswordUseCase>(relaxed = true)
    val validateEmailUseCase = mockk<ValidateEmailUseCase>(relaxed = true)
}
