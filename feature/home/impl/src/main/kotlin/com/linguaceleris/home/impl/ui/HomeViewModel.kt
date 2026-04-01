package com.linguaceleris.home.impl.ui

import com.linguaceleris.auth.api.startWithSignIn
import com.linguaceleris.home.impl.domain.GetDayTasksUseCase
import com.linguaceleris.home.impl.domain.SignOutUseCase
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quiz.api.navigateToQuiz
import com.linguaceleris.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getDayTasksUseCase: GetDayTasksUseCase,
    private val signOutUseCase: SignOutUseCase,
) : BaseViewModel<HomeUiState, HomeEvent>(initialState = HomeUiState()) {

    init {
        loadQuizzes()
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnQuizClick -> navigator.navigateToQuiz(event.quizId)
            HomeEvent.OnOpenMenuClick -> updateState { onOpenMenuClick() }
            HomeEvent.OnCloseMenuClick -> updateState { onCloseMenuClick() }
            HomeEvent.OnSettingsClick -> {}
            HomeEvent.OnSignOutClick -> onSignOutClick()
        }
    }

    private fun onSignOutClick() {
        updateState { onCloseMenuClick() }
        signOutUseCase()
        navigator.startWithSignIn()
    }

    private fun loadQuizzes() {
        launch {
            val quizzes = getDayTasksUseCase()
            updateState { quizzesLoaded(quizzes) }
        }
    }
}
