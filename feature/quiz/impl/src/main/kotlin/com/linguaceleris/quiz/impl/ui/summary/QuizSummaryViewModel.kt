package com.linguaceleris.quiz.impl.ui.summary

import com.linguaceleris.home.api.backToHomeWithResult
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quiz.api.QuizDifficulty
import com.linguaceleris.quiz.impl.domain.UpdateStreakUseCase
import com.linguaceleris.ui.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

private const val ASSISTED_DIFFICULTY = "difficulty"
private const val ASSISTED_IS_SUCCESSFUL = "isSuccessful"

@HiltViewModel(assistedFactory = QuizSummaryViewModel.Factory::class)
internal class QuizSummaryViewModel @AssistedInject constructor(
    private val navigator: Navigator,
    private val updateStreakUseCase: UpdateStreakUseCase,
    @Assisted(ASSISTED_DIFFICULTY) private val difficulty: QuizDifficulty,
    @Assisted(ASSISTED_IS_SUCCESSFUL) private val isSuccessful: Boolean,
) : BaseViewModel<QuizSummaryUiState, QuizSummaryEvent>(
    initialState = QuizSummaryUiState(
        isSuccessful = isSuccessful,
    ),
) {

    init {
        if (isSuccessful) launch { updateStreakUseCase(difficulty) }
    }

    override fun onEvent(event: QuizSummaryEvent) {
        when (event) {
            QuizSummaryEvent.OnTryAgainClicked -> launch {
                navigator.backToHomeWithResult(isSuccessful)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted(ASSISTED_DIFFICULTY) difficulty: QuizDifficulty,
            @Assisted(ASSISTED_IS_SUCCESSFUL) isSuccessful: Boolean,
        ): QuizSummaryViewModel
    }
}
