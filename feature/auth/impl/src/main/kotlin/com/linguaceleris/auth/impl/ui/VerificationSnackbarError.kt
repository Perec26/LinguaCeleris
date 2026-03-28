package com.linguaceleris.auth.impl.ui

import com.linguaceleris.auth.impl.R
import com.linguaceleris.ui.utils.UiText

internal enum class VerificationSnackbarError(val message: UiText.StringResource) {
    EMAIL(UiText.StringResource(R.string.auth_email_verification_error)),
    SENDING(UiText.StringResource(R.string.auth_sending_verification_error)),
}
