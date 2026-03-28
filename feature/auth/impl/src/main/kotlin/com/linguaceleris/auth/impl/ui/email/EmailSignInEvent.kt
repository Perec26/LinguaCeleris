package com.linguaceleris.auth.impl.ui.email

internal sealed class EmailSignInEvent {
    data class OnEmailChanged(
        val email: String,
    ) : EmailSignInEvent()

    data class OnPasswordChanged(
        val password: String,
    ) : EmailSignInEvent()

    data object OnEnterClick : EmailSignInEvent()
    data object OnPasswordVisibilityChanged : EmailSignInEvent()
    data object OnBackClicked : EmailSignInEvent()
    data object OnForgotPasswordClicked : EmailSignInEvent()
}
