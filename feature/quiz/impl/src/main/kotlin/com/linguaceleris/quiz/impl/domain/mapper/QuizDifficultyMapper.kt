package com.linguaceleris.quiz.impl.domain.mapper

import com.linguaceleris.quiz.api.QuizLevel
import com.linguaceleris.quiz.model.QuizDifficultyDTO
import com.linguaceleris.streak.model.StreakDifficultyDTO

fun QuizLevel.toDTO() = when (this) {
    QuizLevel.BASIC -> QuizDifficultyDTO.BASIC
    QuizLevel.INTERMEDIATE -> QuizDifficultyDTO.INTERMEDIATE
    QuizLevel.ADVANCED -> QuizDifficultyDTO.ADVANCED
}

// TODO: убрать дублирование
fun QuizLevel.toStreakDTO() = when (this) {
    QuizLevel.BASIC -> StreakDifficultyDTO.BASIC
    QuizLevel.INTERMEDIATE -> StreakDifficultyDTO.INTERMEDIATE
    QuizLevel.ADVANCED -> StreakDifficultyDTO.ADVANCED
}
