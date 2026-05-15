package com.linguaceleris.auth.impl.domain

import com.linguaceleris.auth.AuthRepository
import com.linguaceleris.testing.ExcludeFromKover
import javax.inject.Inject

@ExcludeFromKover
internal class SendEmailVerificationUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke() = repository.sendEmailVerification()
}
