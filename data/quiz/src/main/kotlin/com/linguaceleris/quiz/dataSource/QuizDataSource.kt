package com.linguaceleris.quiz.dataSource

import com.google.firebase.firestore.FirebaseFirestore
import com.linguaceleris.network.toDataClass
import com.linguaceleris.quiz.model.QuizDTO
import com.linguaceleris.quiz.model.ScheduleDTO
import com.linguaceleris.testing.ExcludeFromKover
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

private const val QUIZ_COLLECTION_NAME = "quizzes"
private const val QUIZ_META_COLLECTION_NAME = "quiz_meta"
private const val QUIZ_META_SCHEDULE_ID = "schedule"

@ExcludeFromKover
class QuizDataSource @Inject constructor(private val fireStore: FirebaseFirestore,) {

    suspend fun getQuiz(quizId: String) = fireStore.collection(QUIZ_COLLECTION_NAME)
        .document(quizId)
        .get()
        .await()
        .toDataClass<QuizDTO>()

    suspend fun getSchedule() = fireStore.collection(QUIZ_META_COLLECTION_NAME)
        .document(QUIZ_META_SCHEDULE_ID)
        .get()
        .await()
        .toDataClass<ScheduleDTO>()
}
