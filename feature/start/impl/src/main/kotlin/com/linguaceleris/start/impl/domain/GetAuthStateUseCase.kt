package com.linguaceleris.start.impl.domain

import com.linguaceleris.auth.AuthRepository
import javax.inject.Inject

internal class GetAuthStateUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke() = repository.getAuthState()
}
