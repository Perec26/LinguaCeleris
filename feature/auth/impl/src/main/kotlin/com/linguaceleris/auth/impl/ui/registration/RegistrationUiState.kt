package com.linguaceleris.auth.impl.ui.registration

import androidx.annotation.StringRes
import com.linguaceleris.auth.impl.R
import com.linguaceleris.auth.impl.ui.registration.model.AuthValidationResult

internal data class RegistrationUiState(
    val nickname: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isRegistrationInProgress: Boolean = false,
    val isSendingVerificationInProgress: Boolean = false,
    val registrationState: RegistrationState? = null,
    val showEmailVerificationDialog: Boolean = false,
    val validationState: RegistrationValidationState = RegistrationValidationState(),
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

    fun onValidationStateChanged(validationState: RegistrationValidationState) = copy(
        validationState = validationState,
        registrationState = null,
    )

    fun onRegistrationStarted() = copy(isRegistrationInProgress = true)

    fun onRegistrationFinished(registrationState: RegistrationState) = copy(
        registrationState = registrationState,
        isRegistrationInProgress = false,
    )

    fun onSendingVerificationStarted() = copy(isSendingVerificationInProgress = true)

    fun onSendingVerificationFinished() = copy(isSendingVerificationInProgress = false)

    fun setSendAgainTimer(sendAgainTimer: Int) = copy(sendAgainTimer = sendAgainTimer)
}

internal data class RegistrationValidationState(
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

internal enum class RegistrationState(
    @param:StringRes val message: Int,
) {
    SUCCESS(R.string.auth_registration_success),
    USER_EXIST(R.string.auth_registration_user_exist),
    WEAK_PASSWORD(R.string.auth_registration_weak_password),
    INVALID_CREDENTIALS(R.string.auth_registration_invalid_credentials),
    UNKNOWN_ERROR(R.string.auth_unknown_error)
}
