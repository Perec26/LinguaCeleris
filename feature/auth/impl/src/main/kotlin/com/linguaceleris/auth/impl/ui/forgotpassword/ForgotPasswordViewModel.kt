package com.linguaceleris.auth.impl.ui.forgotpassword

import com.linguaceleris.auth.api.startWithSignIn
import com.linguaceleris.auth.impl.domain.ResetPasswordUseCase
import com.linguaceleris.auth.impl.domain.ValidateEmailUseCase
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.ui.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = ForgotPasswordViewModel.Factory::class)
internal class ForgotPasswordViewModel @AssistedInject constructor(
    val navigator: Navigator,
    val resetPasswordUseCase: ResetPasswordUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    @Assisted val email: String,
) : BaseViewModel<ForgotPasswordUiState, ForgotPasswordEvent>(
    initialState = ForgotPasswordUiState(
        email = email,
    ),
) {

    override fun onEvent(event: ForgotPasswordEvent) {
        when (event) {
            ForgotPasswordEvent.OnBackClicked -> onBackClicked()
            is ForgotPasswordEvent.OnEmailChanged -> updateState { onEmailChanged(event.email) }
            ForgotPasswordEvent.OnResetClick -> onResetClick()
        }
    }

    private fun onBackClicked() {
        if (currentState.showSuccess) navigator.startWithSignIn() else navigator.back()
    }

    private fun onResetClick() {
        val validationState = ForgotPasswordValidationState(
            email = validateEmailUseCase(currentState.email),
        )

        if (!validationState.isSuccessful) {
            updateState { onValidationStateChanged(validationState) }
            return
        }
        updateState { onLoadingStarted() }

        launch(
            onError = { },
            doFinally = { updateState { onPasswordReset() } },
        ) {
            resetPasswordUseCase(currentState.email)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(email: String): ForgotPasswordViewModel
    }
}
