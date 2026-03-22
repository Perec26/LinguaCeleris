package com.linguaceleris.domain

import com.linguaceleris.auth.AuthRepository
import javax.inject.Inject

internal class GetIsLoggedIn @Inject constructor(
    private val repository: AuthRepository,
) {
    operator fun invoke(): Boolean = repository.isLoggedIn()
}
