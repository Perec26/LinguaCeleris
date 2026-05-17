package com.linguaceleris.auth

import com.linguaceleris.network.CredentialService
import javax.inject.Inject

class AuthRepository @Inject constructor(private val credentialService: CredentialService) {

    suspend fun signInWithGoogle(idToken: String): Boolean =
        credentialService.signInWithGoogle(idToken)

    suspend fun linkWithGoogle(idToken: String): Boolean = credentialService.linkWithGoogle(idToken)

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

    fun getAuthState() = when {
        !credentialService.isLoggedIn() -> AuthState.NOT_LOGGED_IN
        credentialService.isAnonymous() -> AuthState.ANONYMOUS
        !credentialService.getEmailVerification() -> AuthState.EMAIL_NOT_VERIFIED
        else -> AuthState.LOGGED_IN
    }

    fun isGuest() = credentialService.isAnonymous()

    fun getWebClientId() = credentialService.getWebClientId()

    suspend fun signInAnonymously() = credentialService.signInAnonymously()

    fun getCurrentUserId() = credentialService.getCurrentUserId()
}
