package com.linguaceleris.home.impl.domain

import com.linguaceleris.auth.AuthRepository
import javax.inject.Inject

internal class IsGuestUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke() = repository.isGuest()
}
