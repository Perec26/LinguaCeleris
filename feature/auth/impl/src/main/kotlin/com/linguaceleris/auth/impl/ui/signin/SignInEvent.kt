package com.linguaceleris.auth.impl.ui.signin

internal sealed class SignInEvent {
    data class OnGoogleTokenReceived(
        val idToken: String,
    ) : SignInEvent()

    data object OnEmailSignInClick : SignInEvent()
    data object OnSignInAsGuestClick : SignInEvent()
    data object OnGoogleSignInClick : SignInEvent()
}
