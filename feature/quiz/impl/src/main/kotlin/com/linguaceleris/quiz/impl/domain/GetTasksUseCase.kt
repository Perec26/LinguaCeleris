package com.linguaceleris.quiz.impl.domain

import com.linguaceleris.quiz.QuizRepository
import com.linguaceleris.quiz.impl.domain.mapper.toUi
import com.linguaceleris.quiz.impl.ui.model.TaskUI
import javax.inject.Inject

private const val TASK_REQUIRED_NUMBER = 12

internal class GetTasksUseCase @Inject constructor(val repository: QuizRepository) {
    suspend operator fun invoke(quizId: String): List<TaskUI> {
        val quiz = repository.getQuiz(quizId)
        val tasks = quiz?.tasks?.toUi() ?: emptyList()
        if (tasks.size < TASK_REQUIRED_NUMBER) {
            return (tasks + (quiz?.extraPool?.toUi() ?: emptyList())).shuffled()
        }
        return tasks.shuffled()
    }
}
