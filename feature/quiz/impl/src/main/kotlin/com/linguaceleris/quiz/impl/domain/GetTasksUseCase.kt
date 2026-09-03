package com.linguaceleris.quiz.impl.domain

import com.linguaceleris.quiz.QuizRepository
import com.linguaceleris.quiz.api.QuizLevel
import com.linguaceleris.quiz.impl.domain.mapper.toDTO
import com.linguaceleris.quiz.impl.domain.mapper.toUi
import javax.inject.Inject
import kotlinx.coroutines.flow.map

internal class GetTasksUseCase @Inject constructor(val repository: QuizRepository) {

    suspend operator fun invoke(quizLevel: QuizLevel) = repository.getTasks(quizLevel.toDTO())
        .map { it.toUi() }
}
