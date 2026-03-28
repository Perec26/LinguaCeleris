package com.linguaceleris.auth.impl.ui.emailverification

import com.linguaceleris.auth.impl.ui.VerificationSnackbarError

internal sealed class EmailVerificationEffect {
    data object OpenEmail : EmailVerificationEffect()
    data class ShowSnackbarError(val error: VerificationSnackbarError) : EmailVerificationEffect()
}
