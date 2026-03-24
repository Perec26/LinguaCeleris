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

        return authResult.user != null
    }

    fun isLoggedIn(): Boolean = currentUser != null

    fun getWebClientId(): String = webClientId

    fun signOut(): Boolean {
        auth.signOut()
        return currentUser == null
    }

    suspend fun register(nickname: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
        val updateRequest = userProfileChangeRequest {
            displayName = nickname
        }
        currentUser?.updateProfile(updateRequest)?.await()
        sendEmailVerification()
    }

    suspend fun sendEmailVerification() {
        currentUser?.sendEmailVerification()?.await()
    }

    fun getEmailVerification(): Boolean {
        currentUser?.reload()
        return currentUser?.isEmailVerified ?: false
    }
}
