package com.linguaceleris.auth.impl.ui.registration

import com.linguaceleris.auth.impl.ui.registration.model.AuthValidationResult

internal data class RegistrationUiState(
    val nickname: String = "Test",
    val email: String = "pepecprodakshn@gmail.com",
    val password: String = "12345678",
    val confirmPassword: String = "12345678",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isRegistrationInProgress: Boolean = false,
    val isSendingVerificationInProgress: Boolean = false,
    val showEmailConfirmationText: Boolean = false,
    val showEmailVerificationDialog: Boolean = false,
    val validationState: ValidationState = ValidationState(),
    val sendAgainTimer: Int = 0,
) {
    val sendAgainEnable = sendAgainTimer == 0

    fun onNicknameChanged(nickname: String) = copy(
        nickname = nickname,
        validationState = validationState.copy(nickname = AuthValidationResult.Success),
    )

    fun onEmailChanged(email: String) = copy(
        email = email,
        validationState = validationState.copy(email = AuthValidationResult.Success),
    )

    fun onPasswordChanged(password: String) = copy(
        password = password,
        validationState = validationState.copy(password = AuthValidationResult.Success),
    )

    fun onConfirmPasswordChanged(confirmPassword: String) = copy(
        confirmPassword = confirmPassword,
        validationState = validationState.copy(confirmPassword = AuthValidationResult.Success),
    )

    fun onPasswordVisibilityChanged() = copy(isPasswordVisible = !isPasswordVisible)

    fun onConfirmPasswordVisibilityChanged() =
        copy(isConfirmPasswordVisible = !isConfirmPasswordVisible)

    fun onShowEmailVerificationDialog() = copy(showEmailVerificationDialog = true)

    fun onHideEmailVerificationDialog() = copy(showEmailVerificationDialog = false)

    fun onValidationStateChanged(validationState: ValidationState) =
        copy(validationState = validationState)

    fun onRegistrationStarted() = copy(isRegistrationInProgress = true)

    fun onRegistrationFinished() = copy(
        showEmailConfirmationText = true,
        isRegistrationInProgress = false,
    )

    fun onSendingVerificationStarted() = copy(isSendingVerificationInProgress = true)

    fun onSendingVerificationFinished() = copy(isSendingVerificationInProgress = false)

    fun setSendAgainTimer(sendAgainTimer: Int) = copy(sendAgainTimer = sendAgainTimer)
}

internal data class ValidationState(
    val nickname: AuthValidationResult = AuthValidationResult.Success,
    val email: AuthValidationResult = AuthValidationResult.Success,
    val password: AuthValidationResult = AuthValidationResult.Success,
    val confirmPassword: AuthValidationResult = AuthValidationResult.Success,
) {
    val isSuccessful: Boolean = nickname is AuthValidationResult.Success &&
        email is AuthValidationResult.Success &&
        password is AuthValidationResult.Success &&
        confirmPassword is AuthValidationResult.Success
}
