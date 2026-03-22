package com.linguaceleris.ui

import com.linguaceleris.auth.api.SignInNavKey
import com.linguaceleris.domain.GetIsLoggedIn
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quizselection.api.QuizSelectionNavKey
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getIsLoggedIn: GetIsLoggedIn,
) : BaseViewModel<MainState, MainEvent>(initialState = MainState) {

    init {
        if (getIsLoggedIn()) {
            navigator.navigateTo(QuizSelectionNavKey)
        } else {
            navigator.navigateTo(SignInNavKey)
        }
    }

    override fun onEvent(event: MainEvent) {
    }
}
