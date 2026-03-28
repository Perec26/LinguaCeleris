package com.linguaceleris.auth.impl.ui.emailverification

internal sealed class EmailVerificationEvent {
    data object OnBackClicked : EmailVerificationEvent()
    data object OnOpenMailClicked : EmailVerificationEvent()
    data object OnSendAgainClicked : EmailVerificationEvent()
    data object OnContinueClicked : EmailVerificationEvent()
    data object OnVerifyEmailClicked : EmailVerificationEvent()
    data object OnExitClicked : EmailVerificationEvent()
    data object OnHideEmailVerificationDialog : EmailVerificationEvent()
}
