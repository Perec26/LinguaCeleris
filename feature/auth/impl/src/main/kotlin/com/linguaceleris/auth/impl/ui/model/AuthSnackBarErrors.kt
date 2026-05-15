package com.linguaceleris.auth.impl.ui.model

import com.linguaceleris.auth.impl.R
import com.linguaceleris.ui.SnackBarError
import com.linguaceleris.ui.utils.UiText

data object SignInWithGoogleError :
    SnackBarError(UiText.StringResource(R.string.auth_sign_in_with_google_error))

data object SignInWithGoogleUserCollisionError :
    SnackBarError(UiText.StringResource(R.string.auth_sign_in_with_google_user_collision_error))

data object SignInWithAnonymouslyError :
    SnackBarError(UiText.StringResource(R.string.auth_sign_in_anonymous_error))
