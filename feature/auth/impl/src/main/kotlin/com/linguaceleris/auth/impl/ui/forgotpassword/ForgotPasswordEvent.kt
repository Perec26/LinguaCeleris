package com.linguaceleris.auth.impl.ui.forgotpassword

internal sealed class ForgotPasswordEvent {
    data object OnBackClicked : ForgotPasswordEvent()
    data object OnResetClick : ForgotPasswordEvent()
    data class OnEmailChanged(val email: String) : ForgotPasswordEvent()
}
