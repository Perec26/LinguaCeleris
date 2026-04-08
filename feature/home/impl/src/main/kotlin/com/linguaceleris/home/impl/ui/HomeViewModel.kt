package com.linguaceleris.home.impl.ui

import com.linguaceleris.auth.api.startWithSignIn
import com.linguaceleris.home.api.QuizResult
import com.linguaceleris.home.impl.domain.GetStreakUseCase
import com.linguaceleris.home.impl.domain.LoadScheduleUseCase
import com.linguaceleris.home.impl.domain.SignOutUseCase
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quiz.api.navigateToEasyQuiz
import com.linguaceleris.quiz.api.navigateToHardQuiz
import com.linguaceleris.quiz.api.navigateToMediumQuiz
import com.linguaceleris.settins.api.navigateToSettings
import com.linguaceleris.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val navigator: Navigator,
    private val loadScheduleUseCase: LoadScheduleUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val getStreakUseCase: GetStreakUseCase,
) : BaseViewModel<HomeUiState, HomeEvent>(initialState = HomeUiState()) {

    init {
        subscribeToNavigationResult()
        loadQuizzes()
    }

    private fun subscribeToNavigationResult() {
        launch {
            navigator.getResultFlow<QuizResult>().collect {
                updateState { startLoading() }
                val streak = getStreakUseCase()
                updateState { dataLoaded(streak = streak) }
            }
        }
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnOpenMenuClick -> updateState { onOpenMenuClick() }
            HomeEvent.OnCloseMenuClick -> updateState { onCloseMenuClick() }
            HomeEvent.OnSettingsClick -> onSettingsClick()
            HomeEvent.OnSignOutClick -> onSignOutClick()
            HomeEvent.OnEastQuizClick -> navigator.navigateToEasyQuiz()
            HomeEvent.OnHardQuizClick -> navigator.navigateToHardQuiz()
            HomeEvent.OnMediumQuizClick -> navigator.navigateToMediumQuiz()
        }
    }

    private fun onSettingsClick() {
        updateState { onCloseMenuClick() }
        navigator.navigateToSettings()
    }

    private fun onSignOutClick() {
        updateState { onCloseMenuClick() }
        signOutUseCase()
        navigator.startWithSignIn()
    }

    private fun loadQuizzes() {
        launch {
            loadScheduleUseCase()
            val streak = getStreakUseCase()
            updateState { dataLoaded(streak = streak) }
        }
    }
}
