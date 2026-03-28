package com.linguaceleris.auth.impl.ui.forgotpassword

import com.linguaceleris.auth.impl.ui.registration.model.AuthValidationResult

internal data class ForgotPasswordUiState(
    val email: String = "",
    val validationState: ForgotPasswordValidationState = ForgotPasswordValidationState(),
    val isLoading: Boolean = false,
    val showSuccess: Boolean = false,
) {
    fun onEmailChanged(email: String) = copy(email = email)

    fun onValidationStateChanged(state: ForgotPasswordValidationState) =
        copy(validationState = state)

    fun onLoadingStarted() = copy(isLoading = true)

    fun onPasswordReset() = copy(
        isLoading = false,
        showSuccess = true,
    )
}

internal data class ForgotPasswordValidationState(
    val email: AuthValidationResult = AuthValidationResult.Success,
) {
    val isSuccessful: Boolean = email is AuthValidationResult.Success
}
