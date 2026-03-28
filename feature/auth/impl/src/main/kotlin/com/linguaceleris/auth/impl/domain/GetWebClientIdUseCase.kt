package com.linguaceleris.auth.impl.domain

import com.linguaceleris.auth.AuthRepository
import javax.inject.Inject

internal class GetWebClientIdUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    operator fun invoke() = repository.getWebClientId()
}
