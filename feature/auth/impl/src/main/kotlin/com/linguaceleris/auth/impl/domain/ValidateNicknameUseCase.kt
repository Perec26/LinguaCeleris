package com.linguaceleris.auth.impl.domain

import com.linguaceleris.auth.impl.ui.registration.model.AuthValidationResult
import javax.inject.Inject

private const val MIN_LENGTH = 2
private const val MAX_LENGTH = 20

internal class ValidateNicknameUseCase @Inject constructor() {

    operator fun invoke(nickname: String): AuthValidationResult {
        if (nickname.isBlank()) return AuthValidationResult.Error.EmptyField
        if (nickname.length < MIN_LENGTH) return AuthValidationResult.Error.NicknameTooShort
        if (nickname.length > MAX_LENGTH) return AuthValidationResult.Error.NicknameTooLong
        return AuthValidationResult.Success
    }
}
