package com.linguaceleris.auth.impl.ui.registration

import com.linguaceleris.auth.impl.ui.VerificationSnackbarError

internal sealed class RegistrationEffect {
    data object OpenEmail : RegistrationEffect()
    data class ShowSnackbarError(val error: VerificationSnackbarError) : RegistrationEffect()
}
