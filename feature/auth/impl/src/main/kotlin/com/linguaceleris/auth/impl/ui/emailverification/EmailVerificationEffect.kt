package com.linguaceleris.auth.impl.ui.emailverification

import com.linguaceleris.auth.impl.R
import com.linguaceleris.ui.utils.UiText

internal sealed class EmailVerificationEffect {
    data object OpenEmail : EmailVerificationEffect()
    data class ShowSnackbarError(
        val error: SnackbarError,
    ) : EmailVerificationEffect()
}

internal enum class SnackbarError(
    val message: UiText.StringResource,
) {
    EMAIL_VERIFICATION_ERROR(UiText.StringResource(R.string.auth_email_verification_error)),
    SENDING_VERIFICATION_ERROR(UiText.StringResource(R.string.auth_sending_verification_error)),
}
