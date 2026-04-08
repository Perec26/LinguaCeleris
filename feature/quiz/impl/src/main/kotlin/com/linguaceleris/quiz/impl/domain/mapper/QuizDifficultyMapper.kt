package com.linguaceleris.quiz.impl.domain.mapper

import com.linguaceleris.quiz.api.QuizDifficulty
import com.linguaceleris.quiz.model.QuizDifficultyDTO
import com.linguaceleris.streak.model.StreakDifficultyDTO

fun QuizDifficulty.toDTO() = when (this) {
    QuizDifficulty.BASIC -> QuizDifficultyDTO.BASIC
    QuizDifficulty.INTERMEDIATE -> QuizDifficultyDTO.INTERMEDIATE
    QuizDifficulty.ADVANCED -> QuizDifficultyDTO.ADVANCED
}

// TODO: убрать дублирование
fun QuizDifficulty.toStreakDTO() = when (this) {
    QuizDifficulty.BASIC -> StreakDifficultyDTO.BASIC
    QuizDifficulty.INTERMEDIATE -> StreakDifficultyDTO.INTERMEDIATE
    QuizDifficulty.ADVANCED -> StreakDifficultyDTO.ADVANCED
}
