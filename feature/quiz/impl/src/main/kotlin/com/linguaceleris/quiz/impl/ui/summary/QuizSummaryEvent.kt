package com.linguaceleris.quiz.impl.ui.summary

internal sealed class QuizSummaryEvent {
    data object OnTryAgainClicked : QuizSummaryEvent()
}
