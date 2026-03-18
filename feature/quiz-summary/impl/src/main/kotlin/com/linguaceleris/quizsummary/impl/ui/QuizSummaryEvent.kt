package com.linguaceleris.quizsummary.impl.ui

internal sealed class QuizSummaryEvent {
    data object OnTryAgainClicked : QuizSummaryEvent()
}
