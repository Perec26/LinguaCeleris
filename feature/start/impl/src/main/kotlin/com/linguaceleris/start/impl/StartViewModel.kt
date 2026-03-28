package com.linguaceleris.start.impl

import androidx.navigation3.runtime.NavKey
import com.linguaceleris.auth.AuthState
import com.linguaceleris.auth.api.EmailVerificationNavKey
import com.linguaceleris.auth.api.SignInNavKey
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quizselection.api.QuizSelectionNavKey
import com.linguaceleris.start.impl.domain.GetAuthStateUseCase
import com.linguaceleris.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class StartViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getAuthStateUseCase: GetAuthStateUseCase,
) : BaseViewModel<StartUiState, StartEvent>(initialState = StartUiState()) {

    init {
        val startDestination: NavKey = when (getAuthStateUseCase()) {
            AuthState.NOT_LOGGED_IN -> SignInNavKey
            AuthState.EMAIL_NOT_VERIFIED -> EmailVerificationNavKey(true)
            AuthState.LOGGED_IN, AuthState.ANONYMOUS -> QuizSelectionNavKey
        }
        navigator.startWith(startDestination)
    }

    override fun onEvent(event: StartEvent) {}
}
