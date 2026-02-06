package com.linguaceleris.quiz

import com.linguaceleris.quiz.dataSource.QuizDataSource
import com.linguaceleris.quiz.model.QuizDTO
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val dataSource: QuizDataSource,
) {

    suspend fun getQuiz(): QuizDTO? = dataSource.getQuiz()
}
