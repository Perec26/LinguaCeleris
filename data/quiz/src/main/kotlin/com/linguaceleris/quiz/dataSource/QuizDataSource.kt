package com.linguaceleris.quiz.dataSource

import com.linguaceleris.network.FirestoreService
import com.linguaceleris.network.toDataClass
import com.linguaceleris.quiz.model.QuizDTO
import jakarta.inject.Inject

private const val QUIZ_COLLECTION_NAME = "quizzes"

class QuizDataSource @Inject constructor(
    private val service: FirestoreService,
) {
    suspend fun getQuiz(): QuizDTO? {
        val querySnapshot = service.getCollection(QUIZ_COLLECTION_NAME)
        return querySnapshot?.documents?.firstOrNull()?.toDataClass<QuizDTO>()
    }
}
