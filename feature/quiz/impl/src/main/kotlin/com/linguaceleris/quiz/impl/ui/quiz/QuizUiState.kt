package com.linguaceleris.quiz.impl.ui.quiz

import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI
import com.linguaceleris.ui.ScreenState

internal data class QuizUiState(
    val tasks: List<TaskUI> = emptyList(),
    val currentTaskIndex: Int = 0,
    val currentTask: TaskUI? = null,
    val screenState: ScreenState = ScreenState.LOADING,
    val loadingProgress: Float = 0f,
    val lives: Int = 3,
    val showExitDialog: Boolean = false,
) {
    val progressFloat = currentTaskIndex.toFloat() / tasks.size
    val lastTask = tasks.lastIndex == currentTaskIndex

    fun onTaskLoaded(tasks: List<TaskUI>) = copy(
        tasks = tasks,
        screenState = ScreenState.CONTENT,
        currentTask = tasks.first(),
    )

    fun onError() = copy(screenState = ScreenState.ERROR)

    fun onLoading(process: Float = 0f) = copy(
        screenState = ScreenState.LOADING,
        loadingProgress = process,
    )

    fun onVariantSelected(task: TaskUI) = copy(
        currentTask = task,
        lives = lives - if (task is TaskUI.Matching && task.hasError) 1 else 0,
    )

    fun onTaskChecked(updatedTask: TaskUI.SelectCorrectAnswer): QuizUiState = copy(
        currentTask = updatedTask,
        lives = lives - if (updatedTask.isCorrect) 0 else 1,
    )

    fun showNextTask() = copy(
        currentTaskIndex = currentTaskIndex + 1,
        currentTask = tasks[currentTaskIndex + 1],
    )

    fun showExitDialog() = copy(showExitDialog = true)
    fun hideExitDialog() = copy(showExitDialog = false)
}
