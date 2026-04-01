package com.linguaceleris.home.impl.ui

import com.linguaceleris.home.impl.domain.GetDayTasksUseCase
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quiz.api.navigateToQuiz
import com.linguaceleris.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getDayTasksUseCase: GetDayTasksUseCase,
) : BaseViewModel<HomeUiState, HomeEvent>(initialState = HomeUiState()) {

    init {
        loadQuizzes()
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnOpenMenuClick -> {}
            is HomeEvent.OnQuizClick -> navigator.navigateToQuiz(event.quizId)
        }
    }

    private fun loadQuizzes() {
        launch {
            val quizzes = getDayTasksUseCase()
            updateState { quizzesLoaded(quizzes) }
        }
    }
}
