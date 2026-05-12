package com.linguaceleris.quiz.impl.domain

import com.linguaceleris.quiz.QuizRepository
import com.linguaceleris.quiz.api.QuizLevel
import com.linguaceleris.quiz.impl.domain.mapper.toDTO
import com.linguaceleris.quiz.impl.domain.mapper.toUi
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI
import javax.inject.Inject

private const val TASK_REQUIRED_NUMBER = 12

internal class GetTasksUseCase @Inject constructor(val repository: QuizRepository) {

    suspend operator fun invoke(quizLevel: QuizLevel): List<TaskUI> {
        val quiz = repository.getTasks(quizLevel.toDTO()) ?: error("Quiz is null")
        val tasks = quiz.tasks.toUi()
        if (tasks.size < TASK_REQUIRED_NUMBER) {
            val extraPoolSize = TASK_REQUIRED_NUMBER - tasks.size
            val extraPool = quiz.extraPool.shuffled().take(extraPoolSize).toUi()
            return (tasks + extraPool).shuffled()
        }
        return tasks.shuffled()
    }
}
