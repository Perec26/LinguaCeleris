package com.linguaceleris.auth.impl.domain

import com.linguaceleris.auth.AuthRepository
import javax.inject.Inject

internal class SignInWithGoogleUseCase @Inject constructor(val repository: AuthRepository) {
    suspend operator fun invoke(idToken: String): Boolean = repository.signInWithGoogle(idToken)
}
