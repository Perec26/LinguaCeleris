package com.linguaceleris.network

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class FirestoreService(
    val firestore: FirebaseFirestore,
) {

    suspend fun getCollection(name: String): QuerySnapshot? =
        firestore.collection(name).get().await()
}
