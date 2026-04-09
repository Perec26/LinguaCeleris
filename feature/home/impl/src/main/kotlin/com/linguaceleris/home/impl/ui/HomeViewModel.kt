package com.linguaceleris.home.impl.ui

import com.linguaceleris.auth.api.startWithSignIn
import com.linguaceleris.home.api.QuizResult
import com.linguaceleris.home.impl.R
import com.linguaceleris.home.impl.domain.GetNextDayUseCase
import com.linguaceleris.home.impl.domain.GetStreakUseCase
import com.linguaceleris.home.impl.domain.LoadScheduleUseCase
import com.linguaceleris.home.impl.domain.SignOutUseCase
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quiz.api.navigateToAdvanceQuiz
import com.linguaceleris.quiz.api.navigateToBasicQuiz
import com.linguaceleris.quiz.api.navigateToIntermediateQuiz
import com.linguaceleris.settins.api.navigateToSettings
import com.linguaceleris.ui.EffectViewModel
import com.linguaceleris.ui.utils.UiText
import com.linguaceleris.ui.utils.UiTextArg
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.time.Duration

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val navigator: Navigator,
    private val loadScheduleUseCase: LoadScheduleUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val getStreakUseCase: GetStreakUseCase,
    private val getNextDayUseCase: GetNextDayUseCase,
) : EffectViewModel<HomeUiState, HomeEvent, HomeEffect>(initialState = HomeUiState()) {

    init {
        subscribeToNavigationResult()
        subscribeNextDay()
        loadData()
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnOpenMenuClick -> updateState { onOpenMenuClick() }
            HomeEvent.OnCloseMenuClick -> updateState { onCloseMenuClick() }
            HomeEvent.OnSettingsClick -> onSettingsClick()
            HomeEvent.OnSignOutClick -> onSignOutClick()
            HomeEvent.OnBasicQuizClick -> navigator.navigateToBasicQuiz()
            HomeEvent.OnIntermediateQuizClick -> navigator.navigateToIntermediateQuiz()
            HomeEvent.OnAdvanceQuizClick -> navigator.navigateToAdvanceQuiz()
            HomeEvent.OnTelegramClick -> sendEffect(HomeEffect.OpenTelegram())
            HomeEvent.OnYoutubeClick -> sendEffect(HomeEffect.OpenYoutube())
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

    private fun subscribeNextDay() {
        launch {
            getNextDayUseCase().collect {
                if (it.inWholeSeconds == 0L) loadData()

                val isLastHour = it.inWholeHours == 0L
                val durationString = getDurationString(it)
                updateState { updateNextQuizzesTimer(durationString, isLastHour) }
            }
        }
    }

    private fun getDurationString(duration: Duration): UiText {
        val hours = duration.inWholeHours
        val minutes = duration.inWholeMinutes % 60
        val seconds = duration.inWholeSeconds % 60
        return when {
            hours > 0 -> UiText.DynamicString("%02d:%02d:%02d".format(hours, minutes, seconds))

            minutes > 0 -> UiText.DynamicString("%02d:%02d".format(minutes, seconds))

            else -> UiText.StringResource(
                resId = R.string.home_seconds,
                args = listOf(UiTextArg.IntArg(seconds.toInt())),
            )
        }
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

    private fun loadData() {
        launch {
            updateState { startLoading() }
            loadScheduleUseCase()
            val streak = getStreakUseCase()
            updateState { dataLoaded(streak = streak) }
        }
    }
}
