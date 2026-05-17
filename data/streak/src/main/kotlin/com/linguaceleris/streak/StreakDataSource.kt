package com.linguaceleris.streak

import com.google.firebase.firestore.FirebaseFirestore
import com.linguaceleris.network.toDataClass
import com.linguaceleris.network.toFirebaseMap
import com.linguaceleris.streak.model.StreakDTO
import com.linguaceleris.testing.ExcludeFromKover
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

private const val USERS_COLLECTION = "users"
private const val STREAK_COLLECTION = "streak"
private const val DATA_DOCUMENT = "data"

@ExcludeFromKover
class StreakDataSource @Inject constructor(private val fireStore: FirebaseFirestore,) {

    private fun getDocument(userId: String) = fireStore.collection(USERS_COLLECTION)
        .document(userId)
        .collection(STREAK_COLLECTION)
        .document(DATA_DOCUMENT)

    suspend fun getStreak(userId: String): StreakDTO? {
        val querySnapshot = getDocument(userId).get().await()
        return querySnapshot.toDataClass<StreakDTO>()
    }

    suspend fun updateStreak(userId: String, streak: StreakDTO) {
        val document = getDocument(userId)
        fireStore.runTransaction { it.set(document, streak.toFirebaseMap()) }.await()
    }
}
