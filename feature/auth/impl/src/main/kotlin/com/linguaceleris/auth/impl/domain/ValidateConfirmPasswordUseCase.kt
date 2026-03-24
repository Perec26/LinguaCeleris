package com.linguaceleris.auth.impl.domain

import com.linguaceleris.auth.impl.ui.registration.model.AuthValidationResult
import javax.inject.Inject

internal class ValidateConfirmPasswordUseCase @Inject constructor() {

    operator fun invoke(confirmPassword: String, password: String): AuthValidationResult {
        if (confirmPassword.isBlank()) return AuthValidationResult.Error.EmptyField
        if (confirmPassword != password) return AuthValidationResult.Error.ConfirmPasswordNotMatch
        return AuthValidationResult.Success
    }
}
