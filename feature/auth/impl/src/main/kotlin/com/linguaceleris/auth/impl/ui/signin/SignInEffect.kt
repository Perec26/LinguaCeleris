package com.linguaceleris.auth.impl.ui.signin

internal sealed class SignInEffect {
    data class SignInWithGoogle(
        val webClientId: String
    ) : SignInEffect()
}
