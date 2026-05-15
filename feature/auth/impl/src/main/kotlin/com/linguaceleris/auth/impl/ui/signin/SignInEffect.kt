package com.linguaceleris.auth.impl.ui.signin

import com.linguaceleris.ui.SnackBarError

internal sealed class SignInEffect {
    data class SignInWithGoogle(val webClientId: String) : SignInEffect()
    data class ShowSnackBarError(val error: SnackBarError) : SignInEffect()
}
