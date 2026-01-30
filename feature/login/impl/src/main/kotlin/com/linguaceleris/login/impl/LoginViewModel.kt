package com.linguaceleris.login.impl

import com.linguaceleris.ui.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = LoginViewModel.Factory::class)
internal class LoginViewModel @AssistedInject constructor(
    @Assisted val number: Int,
) : BaseViewModel<LoginUiState, LoginEvent>(initialState = LoginUiState(number)) {

    override fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.SomeThing -> TODO()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(number: Int): LoginViewModel
    }
}
