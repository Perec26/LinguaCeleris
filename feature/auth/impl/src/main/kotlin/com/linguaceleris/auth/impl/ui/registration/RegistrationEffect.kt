package com.linguaceleris.auth.impl.ui.registration

import com.linguaceleris.auth.impl.R
import com.linguaceleris.ui.utils.UiText

internal sealed class RegistrationEffect {
    data object OpenEmail : RegistrationEffect()
    data class ShowSnackbarError(
        val error: SnackbarError
    ) : RegistrationEffect()
}

internal enum class SnackbarError(
    val message: UiText.StringResource,
) {
    EMAIL_VERIFICATION_ERROR(UiText.StringResource(R.string.auth_email_verification_error)),
    SENDING_VERIFICATION_ERROR(UiText.StringResource(R.string.auth_sending_verification_error)),
}
