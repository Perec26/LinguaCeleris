package com.linguaceleris.start.impl

import com.linguaceleris.auth.AuthState
import com.linguaceleris.auth.api.startWithEmailVerification
import com.linguaceleris.auth.api.startWithSignIn
import com.linguaceleris.home.api.startWithHome
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.start.impl.domain.GetAuthStateUseCase
import com.linguaceleris.testing.ExcludeFromKover
import com.linguaceleris.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class StartViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getAuthStateUseCase: GetAuthStateUseCase,
) : BaseViewModel<StartUiState, StartEvent>(initialState = StartUiState()) {

    init {
        when (getAuthStateUseCase()) {
            AuthState.NOT_LOGGED_IN -> navigator.startWithSignIn()
            AuthState.EMAIL_NOT_VERIFIED -> navigator.startWithEmailVerification()
            AuthState.LOGGED_IN, AuthState.ANONYMOUS -> navigator.startWithHome()
        }
    }

    @ExcludeFromKover
    override fun onEvent(event: StartEvent) {}
}
