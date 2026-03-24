package com.linguaceleris.auth

import com.linguaceleris.network.CredentialService
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val credentialService: CredentialService,
) {

    suspend fun signInWithGoogle(idToken: String): Boolean =
        credentialService.signInWithGoogle(idToken)

    fun isLoggedIn(): Boolean = credentialService.isLoggedIn()

    fun signOut() = credentialService.signOut()

    suspend fun register(nickname: String, email: String, password: String) {
        credentialService.register(nickname, email, password)
    }

    suspend fun sendEmailVerification() {
        credentialService.sendEmailVerification()
    }

    fun getEmailVerification(): Boolean = credentialService.getEmailVerification()
}
