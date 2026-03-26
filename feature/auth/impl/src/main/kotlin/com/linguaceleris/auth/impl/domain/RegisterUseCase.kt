package com.linguaceleris.auth.impl.domain

import com.linguaceleris.auth.AuthRepository
import javax.inject.Inject

internal class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository,
) {

    suspend operator fun invoke(nickname: String, email: String, password: String) =
        repository.register(nickname, email, password)
}
