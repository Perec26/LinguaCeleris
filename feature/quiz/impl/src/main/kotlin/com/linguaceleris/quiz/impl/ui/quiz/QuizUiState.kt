package com.linguaceleris.quiz.impl.ui.quiz

import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI
import com.linguaceleris.quiz.impl.ui.quiz.model.WordCardUI

internal data class QuizUiState(
    val tasks: List<TaskUI> = emptyList(),
    val currentTaskIndex: Int = 0,
    val currentTask: TaskUI? = null,
    val screenState: ScreenState = ScreenState.LOADING,
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

    fun onLoading() = copy(screenState = ScreenState.LOADING)

    fun onCardClicked(variant: WordCardUI): QuizUiState {
        val currentTask = currentTask ?: return this
        var liveLost = 0
        val updatedCurrentTask = when (currentTask) {
            is TaskUI.SelectCorrectAnswer -> currentTask.onCardClicked(variant)

            is TaskUI.Matching -> {
                val newTaskState = currentTask.onCardClicked(variant)
                if (newTaskState.hasError) liveLost = 1
                newTaskState
            }
        }
        return copy(
            currentTask = updatedCurrentTask,
            lives = lives - liveLost,
        )
    }

    fun onCheckClicked(): QuizUiState {
        val currentTask = currentTask ?: return this
        return when (currentTask) {
            is TaskUI.SelectCorrectAnswer -> {
                val liveLost = if (currentTask.isCorrect) 0 else 1
                copy(
                    currentTask = currentTask.copy(isChecked = true),
                    lives = lives - liveLost,
                )
            }

            is TaskUI.Matching -> this
        }
    }

    fun showNextTask() = copy(
        currentTaskIndex = currentTaskIndex + 1,
        currentTask = tasks[currentTaskIndex + 1],
    )

    fun showExitDialog() = copy(showExitDialog = true)
    fun hideExitDialog() = copy(showExitDialog = false)
}

internal enum class ScreenState { LOADING, CONTENT, ERROR }

private fun TaskUI.SelectCorrectAnswer.onCardClicked(variant: WordCardUI) =
    copy(selectedVariant = variant)

private fun TaskUI.Matching.onCardClicked(variant: WordCardUI): TaskUI.Matching {
    if (selectedVariant == null) return copy(selectedVariant = variant)
    if (isReselect(variant)) return copy(selectedVariant = variant)
    if (selectedPair?.original == variant || selectedPair?.translation == variant) {
        return copy(
            disabledVariants = disabledVariants + variant + selectedVariant,
            selectedVariant = null,
        )
    }
    return copy(
        errorVariant = variant,
    )
}
