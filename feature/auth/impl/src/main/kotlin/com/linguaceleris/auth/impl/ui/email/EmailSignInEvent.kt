package com.linguaceleris.auth.impl.ui.email

internal sealed class EmailSignInEvent {
    data class OnEmailChange(
        val email: String
    ) : EmailSignInEvent()
    data class OnPasswordChange(
        val password: String
    ) : EmailSignInEvent()

    data object OnNextClick : EmailSignInEvent()
    data object OnPasswordVisibilityChange : EmailSignInEvent()
}
