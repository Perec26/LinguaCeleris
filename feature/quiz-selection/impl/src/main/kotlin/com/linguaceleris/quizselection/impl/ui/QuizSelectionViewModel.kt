package com.linguaceleris.quizselection.impl.ui

import com.linguaceleris.auth.api.startWithSignIn
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quiz.api.QuizNavKey
import com.linguaceleris.quizselection.impl.domain.GetDayTasksUseCase
import com.linguaceleris.quizselection.impl.domain.SignOutUseCase
import com.linguaceleris.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class QuizSelectionViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getDayTasksUseCase: GetDayTasksUseCase,
    private val signOutUseCase: SignOutUseCase,
) : BaseViewModel<QuizSelectionUiState, QuizSelectionEvent>(
    initialState = QuizSelectionUiState(),
) {

    init {
        launch {
            val quizzes = getDayTasksUseCase()
            updateState { quizzesLoaded(quizzes) }
        }
    }

    override fun onEvent(event: QuizSelectionEvent) {
        when (event) {
            is QuizSelectionEvent.OnQuizClick -> navigator.navigateTo(QuizNavKey(event.quizId))

            QuizSelectionEvent.OnSignOut -> {
                signOutUseCase()
                navigator.startWithSignIn()
            }
        }
    }
}
