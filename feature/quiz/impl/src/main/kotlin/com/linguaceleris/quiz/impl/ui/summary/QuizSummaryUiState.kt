package com.linguaceleris.quiz.impl.ui.summary

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.linguaceleris.quiz.api.QuizLevel
import com.linguaceleris.quiz.impl.R
import com.linguaceleris.ui.ScreenState

internal data class QuizSummaryUiState(
    val result: QuizResult = QuizResult.SUCCESSFUL,
    val unfinishedQuizzes: List<QuizLevel> = emptyList(),
    val screenState: ScreenState = ScreenState.LOADING,
) {
    val isSuccessful = result == QuizResult.SUCCESSFUL
    val hasUnfinishedQuizzes = unfinishedQuizzes.isNotEmpty()

    fun onDataLoaded(unfinishedQuizzes: List<QuizLevel>) = copy(
        screenState = ScreenState.CONTENT,
        unfinishedQuizzes = unfinishedQuizzes,
    )

    fun onLoading() = copy(screenState = ScreenState.LOADING)

    fun onError() = copy(screenState = ScreenState.ERROR)
}

internal enum class QuizResult(
    @param:DrawableRes val icon: Int,
    @param:StringRes val title: Int,
    @param:StringRes val description: Int,
) {
    SUCCESSFUL(
        icon = R.drawable.quiz_cup,
        title = R.string.quiz_summary_successful_title,
        description = R.string.quiz_summary_successful_description,
    ),
    FAILURE(
        icon = R.drawable.quiz_cloud,
        title = R.string.quiz_summary_failure_title,
        description = R.string.quiz_summary_failure_description,
    )
}

internal fun Boolean.toQuizResult() = if (this) QuizResult.SUCCESSFUL else QuizResult.FAILURE
