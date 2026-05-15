package com.linguaceleris.auth.impl.ui.linkaccount

internal sealed class LinkAccountEvent {
    data object OnBackClicked : LinkAccountEvent()
    data class OnGoogleTokenReceived(val idToken: String) : LinkAccountEvent()
    data object OnGoogleSignInClick : LinkAccountEvent()
    data class OnGoogleGetCredentialException(val exception: Exception) : LinkAccountEvent()
}
