package com.linguaceleris.auth.impl.ui.email

import com.linguaceleris.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class EmailSignInViewModel @Inject constructor() :
    BaseViewModel<EmailSignInUiState, EmailSignInEvent>(initialState = EmailSignInUiState()) {

    override fun onEvent(event: EmailSignInEvent) {
        when (event) {
            else -> {} // TODO: реализовать ивенты
        }
    }
}
