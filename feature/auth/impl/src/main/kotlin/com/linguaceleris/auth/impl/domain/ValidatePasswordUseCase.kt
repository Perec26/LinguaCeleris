package com.linguaceleris.auth.impl.domain

import com.linguaceleris.auth.impl.ui.registration.model.AuthValidationResult
import javax.inject.Inject

private const val MIN_LENGTH = 8
private const val MAX_LENGTH = 4096

internal class ValidatePasswordUseCase @Inject constructor() {

    operator fun invoke(password: String): AuthValidationResult {
        if (password.isBlank()) return AuthValidationResult.Error.EmptyField
        if (password.length < MIN_LENGTH) return AuthValidationResult.Error.PasswordTooShort
        if (password.length > MAX_LENGTH) return AuthValidationResult.Error.PasswordTooLong
        return AuthValidationResult.Success
    }
}
