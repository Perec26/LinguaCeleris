package com.linguaceleris.home.impl.domain

import com.linguaceleris.auth.AuthRepository
import com.linguaceleris.testing.ExcludeFromKover
import javax.inject.Inject

@ExcludeFromKover
internal class SignOutUseCase @Inject constructor(private val repository: AuthRepository) {

    operator fun invoke() = repository.signOut()
}
