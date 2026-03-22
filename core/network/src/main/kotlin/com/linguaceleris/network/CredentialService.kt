package com.linguaceleris.network

import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.tasks.await

class CredentialService @Inject constructor(
    @param:Named("web_client_id") private val webClientId: String,
) {
    suspend fun signInWithGoogle(idToken: String): Boolean {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)

        val authResult = Firebase.auth
            .signInWithCredential(firebaseCredential)
            .await()

        return authResult.user != null
    }

    fun isLoggedIn(): Boolean = Firebase.auth.currentUser != null

    fun getWebClientId(): String = webClientId

    fun signOut(): Boolean {
        Firebase.auth.signOut()
        return Firebase.auth.currentUser == null
    }
}
