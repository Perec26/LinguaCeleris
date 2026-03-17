package com.linguaceleris.quiz.dataSource

import com.linguaceleris.network.FirestoreService
import com.linguaceleris.network.toDataClass
import com.linguaceleris.quiz.model.QuizDTO
import com.linguaceleris.quiz.model.ScheduleDTO
import javax.inject.Inject

private const val QUIZ_COLLECTION_NAME = "quizzes"
private const val QUIZ_META_COLLECTION_NAME = "quiz_meta"
private const val QUIZ_META_SCHEDULE_ID = "schedule"

class QuizDataSource @Inject constructor(
    private val service: FirestoreService,
) {
    suspend fun getQuiz(quizId: String): QuizDTO? {
        val querySnapshot = service.getCollection(QUIZ_COLLECTION_NAME) ?: return null
        return querySnapshot.documents.firstOrNull {
            it.id == quizId
        }?.toDataClass<QuizDTO>()
    }

    suspend fun getSchedule(): ScheduleDTO? {
        val querySnapshot = service.getCollection(QUIZ_META_COLLECTION_NAME) ?: return null
        val schedule = querySnapshot.documents.firstOrNull { it.id == QUIZ_META_SCHEDULE_ID }
        return schedule?.toDataClass<ScheduleDTO>()
    }
}
