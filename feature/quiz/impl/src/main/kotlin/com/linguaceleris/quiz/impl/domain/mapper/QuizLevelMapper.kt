package com.linguaceleris.quiz.impl.domain.mapper

import com.linguaceleris.quiz.api.QuizLevel
import com.linguaceleris.quiz.model.QuizLevelDTO

fun QuizLevel.toDTO() = when (this) {
    QuizLevel.BASIC -> QuizLevelDTO.BASIC
    QuizLevel.INTERMEDIATE -> QuizLevelDTO.INTERMEDIATE
    QuizLevel.ADVANCED -> QuizLevelDTO.ADVANCED
}
