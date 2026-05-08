package com.linguaceleris.quiz.impl.ui.summary

import com.linguaceleris.quiz.api.QuizLevel

internal sealed class QuizSummaryEvent {
    data class OnNextQuizClick(val level: QuizLevel) : QuizSummaryEvent()
    data object OnTryAgainClicked : QuizSummaryEvent()
    data object OnBackClick : QuizSummaryEvent()
    data object OnReloadData : QuizSummaryEvent()
}
