package com.linguaceleris.quiz.dataSource

import com.google.firebase.firestore.FirebaseFirestore
import com.linguaceleris.config.AppConfig
import com.linguaceleris.network.toDataClass
import com.linguaceleris.quiz.model.QuizDTO
import com.linguaceleris.quiz.model.ScheduleDTO
import com.linguaceleris.testing.ExcludeFromKover
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

private const val QUIZ_COLLECTION_NAME = "quizzes"
private const val QUIZ_META_COLLECTION_NAME = "quiz_meta"
private const val QUIZ_META_SCHEDULE_ID = "schedule"

private const val DEV_QUIZ_COLLECTION_NAME = "dev_quizzes"
private const val DEV_QUIZ_META_COLLECTION_NAME = "dev_quiz_meta"
private const val DEV_QUIZ_META_SCHEDULE_ID = "dev_schedule"

@ExcludeFromKover
class QuizDataSource @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val appConfig: AppConfig,
) {

    private val quizCollectionName = if (appConfig.isDebug) {
        DEV_QUIZ_COLLECTION_NAME
    } else {
        QUIZ_COLLECTION_NAME
    }

    private val quizMetaCollectionName = if (appConfig.isDebug) {
        DEV_QUIZ_META_COLLECTION_NAME
    } else {
        QUIZ_META_COLLECTION_NAME
    }

    private val scheduleCollectionName = if (appConfig.isDebug) {
        DEV_QUIZ_META_SCHEDULE_ID
    } else {
        QUIZ_META_SCHEDULE_ID
    }

    suspend fun getQuiz(quizId: String) = fireStore.collection(quizCollectionName)
        .document(quizId)
        .get()
        .await()
        .toDataClass<QuizDTO>()

    suspend fun getSchedule() = fireStore.collection(quizMetaCollectionName)
        .document(scheduleCollectionName)
        .get()
        .await()
        .toDataClass<ScheduleDTO>()
}
