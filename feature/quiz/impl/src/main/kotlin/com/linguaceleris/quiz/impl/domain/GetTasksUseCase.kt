package com.linguaceleris.quiz.impl.domain

import com.linguaceleris.quiz.QuizRepository
import com.linguaceleris.quiz.api.QuizDifficulty
import com.linguaceleris.quiz.impl.domain.mapper.toDTO
import com.linguaceleris.quiz.impl.domain.mapper.toUi
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI
import javax.inject.Inject

private const val TASK_REQUIRED_NUMBER = 12

internal class GetTasksUseCase @Inject constructor(val repository: QuizRepository) {

    suspend operator fun invoke(quizDifficulty: QuizDifficulty): List<TaskUI> {
        val quiz = repository.getTasks(quizDifficulty.toDTO())
        val tasks = quiz?.tasks?.toUi() ?: emptyList()
        if (tasks.size < TASK_REQUIRED_NUMBER) {
            return (tasks + (quiz?.extraPool?.toUi() ?: emptyList())).shuffled()
        }
        return tasks.shuffled()
    }
}
