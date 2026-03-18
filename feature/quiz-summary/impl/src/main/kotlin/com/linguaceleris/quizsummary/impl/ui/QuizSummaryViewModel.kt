package com.linguaceleris.quizsummary.impl.ui

import com.linguaceleris.navigation.Navigator
import com.linguaceleris.ui.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = QuizSummaryViewModel.Factory::class)
internal class QuizSummaryViewModel @AssistedInject constructor(
    private val navigator: Navigator,
    @Assisted private val isSuccessful: Boolean,
) : BaseViewModel<QuizSummaryUiState, QuizSummaryEvent>(
    initialState = QuizSummaryUiState(
        isSuccessful = isSuccessful,
    ),
) {

    override fun onEvent(event: QuizSummaryEvent) {
        when (event) {
            QuizSummaryEvent.OnTryAgainClicked -> navigator.back()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(isSuccessful: Boolean): QuizSummaryViewModel
    }
}
