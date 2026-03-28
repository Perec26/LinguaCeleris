package com.linguaceleris.network

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.tasks.await

class CredentialService @Inject constructor(
    @param:Named("web_client_id") private val webClientId: String,
) {

    private val auth = Firebase.auth

    private val currentUser: FirebaseUser?
        get() = auth.currentUser

    suspend fun signInWithGoogle(idToken: String): Boolean {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)

        val authResult = Firebase.auth
            .signInWithCredential(firebaseCredential)
            .await()

        reloadUser()
        return authResult.user != null
    }

    fun isLoggedIn(): Boolean = currentUser != null

    fun isAnonymous(): Boolean = currentUser?.isAnonymous == false

    fun getWebClientId(): String = webClientId

    fun signOut() {
        auth.signOut()
    }

    suspend fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    suspend fun updateDisplayName(nickname: String) {
        val updateRequest = userProfileChangeRequest {
            displayName = nickname
        }
        currentUser?.updateProfile(updateRequest)?.await()
    }

    suspend fun sendEmailVerification() {
        currentUser?.sendEmailVerification()?.await()
    }

    suspend fun reloadUser() {
        currentUser?.reload()?.await()
    }

    fun getEmailVerification(): Boolean = currentUser?.isEmailVerified ?: false

    suspend fun signInWithEmail(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
        reloadUser()
    }

    suspend fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    suspend fun signInAnonymously() {
        auth.signInAnonymously().await()
        reloadUser()
    }
}
