package com.linguaceleris.quizselection.impl.domain

import com.linguaceleris.auth.AuthRepository
import javax.inject.Inject

internal class SignOutUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke() {
        repository.signOut()
    }
}
