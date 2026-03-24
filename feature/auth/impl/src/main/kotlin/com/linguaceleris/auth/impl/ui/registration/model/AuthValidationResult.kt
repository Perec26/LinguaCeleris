package com.linguaceleris.auth.impl.ui.registration.model

import androidx.annotation.StringRes
import com.linguaceleris.auth.impl.R

internal sealed class AuthValidationResult(
    val isError: Boolean
) {

    object Success : AuthValidationResult(false)

    sealed class Error(
        @param:StringRes val messageResId: Int,
    ) : AuthValidationResult(true) {
        data object EmptyField : Error(
            messageResId = R.string.auth_validation_error_empty_field,
        )

        data object NicknameTooShort : Error(
            messageResId = R.string.auth_validation_error_nickname_too_short,
        )

        data object NicknameTooLong : Error(
            messageResId = R.string.auth_validation_error_nickname_too_long,
        )

        data object InvalidEmail : Error(
            messageResId = R.string.auth_validation_error_invalid_email,
        )

        data object PasswordTooShort : Error(
            messageResId = R.string.auth_validation_error_password_too_short,
        )

        data object PasswordTooLong : Error(
            messageResId = R.string.auth_validation_error_password_too_long,
        )

        data object ConfirmPasswordNotMatch : Error(
            messageResId = R.string.auth_validation_error_confirm_password_not_match,
        )
    }
}
