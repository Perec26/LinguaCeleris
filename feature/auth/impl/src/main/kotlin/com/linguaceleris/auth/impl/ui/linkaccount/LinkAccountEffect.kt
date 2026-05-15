package com.linguaceleris.auth.impl.ui.linkaccount

import com.linguaceleris.ui.SnackBarError

internal sealed class LinkAccountEffect {
    data class SignInWithGoogle(val webClientId: String) : LinkAccountEffect()
    data class ShowSnackBarError(val error: SnackBarError) : LinkAccountEffect()
}
