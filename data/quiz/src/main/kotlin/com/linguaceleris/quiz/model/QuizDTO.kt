package com.linguaceleris.quiz.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizDTO(
    @SerialName("daily_quiz_id")
    val dailyQuizId: String = "",
    @SerialName("difficulty_level")
    val difficultyLevel: String = "4",
    @SerialName("extra_pool")
    val extraPool: List<QuestionDTO>,
    val questions: List<QuestionDTO>,
)
