package com.linguaceleris.auth.impl.domain

import com.linguaceleris.auth.AuthRepository
import com.linguaceleris.testing.ExcludeFromKover
import javax.inject.Inject

@ExcludeFromKover
internal class LinkWithGoogleUseCase @Inject constructor(val repository: AuthRepository) {
    suspend operator fun invoke(idToken: String): Boolean = repository.linkWithGoogle(idToken)
}
