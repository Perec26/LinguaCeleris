package com.linguaceleris.quiz.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@OptIn(markerClass = [InternalSerializationApi::class])
@Serializable
data class QuizDTO(
    @SerialName("contract_version")
    val contractVersion: String,
    @SerialName("daily_quiz_id")
    val dailyQuizId: String,
    @SerialName("extra_pool")
    val extraPool: List<TaskDTO>,
    @SerialName("questions")
    val tasks: List<TaskDTO>,
)
