package com.linguaceleris.auth.impl.ui.email

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.linguaceleris.auth.api.navigateToEmailVerification
import com.linguaceleris.auth.impl.domain.GetEmailVerificationUseCase
import com.linguaceleris.auth.impl.domain.SignInWithEmailUseCase
import com.linguaceleris.auth.impl.domain.ValidateEmailUseCase
import com.linguaceleris.auth.impl.domain.ValidatePasswordUseCase
import com.linguaceleris.auth.impl.navigation.navigateToForgotPassword
import com.linguaceleris.home.api.startWithHome
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class EmailSignInViewModel @Inject constructor(
    private val navigator: Navigator,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
    private val getEmailVerificationUseCase: GetEmailVerificationUseCase,
) : BaseViewModel<EmailSignInUiState, EmailSignInEvent>(initialState = EmailSignInUiState()) {

    override fun onEvent(event: EmailSignInEvent) {
        when (event) {
            EmailSignInEvent.OnBackClicked -> navigator.back()

            is EmailSignInEvent.OnEmailChanged -> updateState { onEmailChanged(event.email) }

            is EmailSignInEvent.OnPasswordChanged -> updateState {
                onPasswordChanged(event.password)
            }

            EmailSignInEvent.OnPasswordVisibilityChanged -> updateState {
                onPasswordVisibilityChanged()
            }

            EmailSignInEvent.OnForgotPasswordClicked -> {
                navigator.navigateToForgotPassword(currentState.email)
            }

            EmailSignInEvent.OnEnterClick -> onEnterClick()
        }
    }

    private fun onEnterClick() {
        val validationState = EmailSignInValidationState(
            email = validateEmailUseCase(currentState.email),
            password = validatePasswordUseCase(currentState.password),
        )

        if (!validationState.isSuccessful) {
            updateState { onValidationStateChanged(validationState) }
            return
        }

        updateState { onLoadingStarted() }

        launch(
            onError = ::handleSignInError,
            doFinally = { updateState { onLoadingFinished() } },
        ) {
            signInWithEmailUseCase(
                email = currentState.email,
                password = currentState.password,
            )
            if (getEmailVerificationUseCase()) {
                navigator.startWithHome()
            } else {
                navigator.navigateToEmailVerification()
            }
        }
    }

    private fun handleSignInError(exception: Exception) {
        when (exception) {
            is FirebaseAuthInvalidUserException, is FirebaseAuthInvalidCredentialsException -> {
                updateState { onSignInError(EmailSignInError.INVALID_CREDENTIALS) }
            }

            else -> updateState { onSignInError(EmailSignInError.UNKNOWN_ERROR) }
        }
    }
}
