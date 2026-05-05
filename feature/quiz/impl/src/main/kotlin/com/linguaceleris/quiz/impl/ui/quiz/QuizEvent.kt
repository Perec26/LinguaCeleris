package com.linguaceleris.quiz.impl.ui.quiz

import com.linguaceleris.quiz.impl.ui.quiz.model.WordCardUI

internal sealed class QuizEvent {

    data class SelectAnswer(val variant: WordCardUI) : QuizEvent()
    data object OnCheckButtonClick : QuizEvent()
    data object OnContinueButtonClick : QuizEvent()
    data object OnBackClick : QuizEvent()
    data object OnExitConfirmClick : QuizEvent()
    data object OnExitCancelClick : QuizEvent()
    data object OnReloadClick : QuizEvent()
    data class OnAudioClick(val audio: String?) : QuizEvent()
}
