package com.linguaceleris.auth.impl.domain

import android.util.Patterns
import com.linguaceleris.auth.impl.ui.registration.model.AuthValidationResult
import javax.inject.Inject

internal class ValidateEmailUseCase @Inject constructor() {

    operator fun invoke(email: String): AuthValidationResult {
        if (email.isBlank()) return AuthValidationResult.Error.EmptyField
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return AuthValidationResult.Error.InvalidEmail
        }
        return AuthValidationResult.Success
    }
}
