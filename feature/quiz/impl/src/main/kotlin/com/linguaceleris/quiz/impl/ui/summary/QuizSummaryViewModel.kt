package com.linguaceleris.quiz.impl.ui.summary

import com.linguaceleris.home.api.backToHomeWithResult
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quiz.api.QuizLevel
import com.linguaceleris.quiz.api.replaceWithQuiz
import com.linguaceleris.quiz.impl.domain.GetUnfinishedQuizzesUseCase
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
    private val getUnfinishedQuizzesUseCase: GetUnfinishedQuizzesUseCase,
    @Assisted(ASSISTED_DIFFICULTY) private val quizLevel: QuizLevel,
    @Assisted(ASSISTED_IS_SUCCESSFUL) private val isSuccessful: Boolean,
) : BaseViewModel<QuizSummaryUiState, QuizSummaryEvent>(
    initialState = QuizSummaryUiState(result = isSuccessful.toQuizResult()),
) {

    init {
        loadData()
    }

    override fun onEvent(event: QuizSummaryEvent) {
        when (event) {
            QuizSummaryEvent.OnTryAgainClicked -> navigator.replaceWithQuiz(quizLevel)
            QuizSummaryEvent.OnBackClick -> launch { navigator.backToHomeWithResult() }
            is QuizSummaryEvent.OnNextQuizClick -> navigator.replaceWithQuiz(event.level)
            QuizSummaryEvent.OnReloadData -> loadData()
        }
    }

    private fun loadData() {
        updateState { onLoading() }
        launch(onError = { updateState { onError() } }) {
            if (isSuccessful) updateStreakUseCase(quizLevel)
            val unfinishedQuizzes = getUnfinishedQuizzesUseCase()
            updateState { onDataLoaded(unfinishedQuizzes) }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted(ASSISTED_DIFFICULTY) difficulty: QuizLevel,
            @Assisted(ASSISTED_IS_SUCCESSFUL) isSuccessful: Boolean,
        ): QuizSummaryViewModel
    }
}
