package com.linguaceleris.auth.impl.ui.signin

internal sealed class SignInEvent {
    data object OnEmailSignInClick : SignInEvent()
    data object OnSignInAsGuestClick : SignInEvent()
    data object OnRegistrationClick : SignInEvent()
    data object OnAnonymousSignInConfirmClick : SignInEvent()
    data object OnAnonymousSignInCancelClick : SignInEvent()
    data object OnGoogleSignInClick : SignInEvent()
    data class OnGoogleTokenReceived(val idToken: String) : SignInEvent()
    data class OnGoogleGetCredentialException(val exception: Exception) : SignInEvent()
}
