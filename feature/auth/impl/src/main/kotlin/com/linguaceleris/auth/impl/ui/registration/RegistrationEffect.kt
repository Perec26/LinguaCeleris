package com.linguaceleris.auth.impl.ui.registration

internal sealed class RegistrationEffect {
    data object OpenEmail : RegistrationEffect()
}
