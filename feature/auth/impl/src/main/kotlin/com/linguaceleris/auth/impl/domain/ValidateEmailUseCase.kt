package com.linguaceleris.auth.impl.domain

import com.linguaceleris.auth.impl.ui.registration.model.AuthValidationResult
import javax.inject.Inject

internal class ValidateEmailUseCase @Inject constructor() {

    private val emailRegex = Regex(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+",
    )

    operator fun invoke(email: String): AuthValidationResult {
        if (email.isBlank()) return AuthValidationResult.Error.EmptyField
        if (!emailRegex.matches(email)) return AuthValidationResult.Error.InvalidEmail
        return AuthValidationResult.Success
    }
}
