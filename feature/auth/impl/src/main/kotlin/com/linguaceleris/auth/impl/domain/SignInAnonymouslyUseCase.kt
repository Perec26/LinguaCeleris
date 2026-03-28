package com.linguaceleris.auth.impl.domain

import com.linguaceleris.auth.AuthRepository
import javax.inject.Inject

internal class SignInAnonymouslyUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke() = repository.signInAnonymously()
}
