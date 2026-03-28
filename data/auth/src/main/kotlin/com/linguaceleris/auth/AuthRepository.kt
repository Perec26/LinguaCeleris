package com.linguaceleris.auth

import com.linguaceleris.network.CredentialService
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val credentialService: CredentialService,
) {

    suspend fun signInWithGoogle(idToken: String): Boolean =
        credentialService.signInWithGoogle(idToken)

    fun signOut() = credentialService.signOut()

    suspend fun register(nickname: String, email: String, password: String) {
        credentialService.register(email, password)
        credentialService.updateDisplayName(nickname)
        credentialService.sendEmailVerification()
    }

    suspend fun sendEmailVerification() = credentialService.sendEmailVerification()

    suspend fun getEmailVerification(): Boolean {
        credentialService.reloadUser()
        return credentialService.getEmailVerification()
    }

    suspend fun signInWithEmail(email: String, password: String) {
        credentialService.signInWithEmail(email, password)
    }

    suspend fun resetPassword(email: String) = credentialService.resetPassword(email)

    fun getAuthState(): AuthState {
        if (!credentialService.isLoggedIn()) return AuthState.NOT_LOGGED_IN
        if (!credentialService.isAnonymous()) return AuthState.ANONYMOUS
        if (!credentialService.getEmailVerification()) return AuthState.EMAIL_NOT_VERIFIED
        return AuthState.LOGGED_IN
    }

    fun getWebClientId() = credentialService.getWebClientId()

    suspend fun signInAnonymously() = credentialService.signInAnonymously()
}
