package com.linguaceleris.quizselection.impl.ui

internal sealed class QuizSelectionEvent {
    data class OnQuizClick(val quizId: String) : QuizSelectionEvent()

    data object OnSignOut : QuizSelectionEvent()
}
