package com.linguaceleris.quiz.impl.ui

import com.linguaceleris.quiz.impl.ui.model.WordCardUI

internal sealed class QuizEvent {

    data class SelectAnswer(val variant: WordCardUI) : QuizEvent()

    data object OnCheckButtonClick : QuizEvent()
    data object OnContinueButtonClick : QuizEvent()
    data object OnBackClick : QuizEvent()

    data class OnAudioClick(val audio: String?) : QuizEvent()
}
