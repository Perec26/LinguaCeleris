package com.linguaceleris.auth.impl.ui.signin

import com.linguaceleris.auth.impl.R
import com.linguaceleris.ui.utils.UiText

internal sealed class SignInEffect {
    data class SignInWithGoogle(val webClientId: String) : SignInEffect()
    data class SnackBarError(val error: SignInError) : SignInEffect()
}

internal enum class SignInError(val message: UiText.StringResource) {
    SIGN_IN_WITH_GOOGLE_ERROR(UiText.StringResource(R.string.auth_sign_in_with_google_error)),
    SIGN_IN_WITH_ANONYMOUSLY_ERROR(UiText.StringResource(R.string.auth_sign_in_anonymous_error))
}
