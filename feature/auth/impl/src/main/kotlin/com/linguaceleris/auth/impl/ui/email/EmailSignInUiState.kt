package com.linguaceleris.auth.impl.ui.email

import androidx.annotation.StringRes
import com.linguaceleris.auth.impl.R
import com.linguaceleris.auth.impl.ui.registration.model.AuthValidationResult

internal data class EmailSignInUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val validationState: EmailSignInValidationState = EmailSignInValidationState(),
    val signInError: EmailSignInError? = null,
) {
    fun onEmailChanged(email: String) = copy(
        email = email,
        validationState = validationState.copy(email = AuthValidationResult.Success),
    )

    fun onPasswordChanged(password: String) = copy(
        password = password,
        validationState = validationState.copy(password = AuthValidationResult.Success),
    )

    fun onPasswordVisibilityChanged() = copy(isPasswordVisible = !isPasswordVisible)

    fun onValidationStateChanged(validationState: EmailSignInValidationState) = copy(
        validationState = validationState,
    )
}

internal data class EmailSignInValidationState(
    val email: AuthValidationResult = AuthValidationResult.Success,
    val password: AuthValidationResult = AuthValidationResult.Success,
) {
    val isSuccessful: Boolean = email is AuthValidationResult.Success &&
        password is AuthValidationResult.Success
}

internal enum class EmailSignInError(@param:StringRes val message: Int) {
    INVALID_CREDENTIALS(R.string.auth_email_sign_in_invalid_credentials),
    UNKNOWN_ERROR(R.string.auth_unknown_error)
}
