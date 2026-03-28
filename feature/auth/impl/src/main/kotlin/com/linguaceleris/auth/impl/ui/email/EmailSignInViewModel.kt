package com.linguaceleris.auth.impl.ui.email

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.linguaceleris.auth.api.EmailVerificationNavKey
import com.linguaceleris.auth.impl.domain.GetEmailVerificationUseCase
import com.linguaceleris.auth.impl.domain.SignInWithEmailUseCase
import com.linguaceleris.auth.impl.domain.ValidateEmailUseCase
import com.linguaceleris.auth.impl.domain.ValidatePasswordUseCase
import com.linguaceleris.auth.impl.navigation.ForgotPasswordNavKey
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quizselection.api.QuizSelectionNavKey
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

            EmailSignInEvent.OnEnterClick -> onEnterClick()

            EmailSignInEvent.OnForgotPasswordClicked -> {
                navigator.navigateTo(ForgotPasswordNavKey(currentState.email))
            }

            is EmailSignInEvent.OnPasswordChanged -> updateState {
                onPasswordChanged(event.password)
            }

            EmailSignInEvent.OnPasswordVisibilityChanged -> updateState {
                onPasswordVisibilityChanged()
            }
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

        launch(
            onError = ::handleSignInError,
            doFinally = { updateState { copy(isLoading = false) } },
        ) {
            updateState { copy(isLoading = true) }
            signInWithEmailUseCase(
                email = currentState.email,
                password = currentState.password,
            )
            if (getEmailVerificationUseCase()) {
                navigator.startWith(QuizSelectionNavKey)
            } else {
                navigator.navigateTo(EmailVerificationNavKey(false))
            }
        }
    }

    private fun handleSignInError(exception: Exception) {
        when (exception) {
            is FirebaseAuthInvalidUserException, is FirebaseAuthInvalidCredentialsException -> {
                updateState { copy(signInError = EmailSignInError.INVALID_CREDENTIALS) }
            }

            else -> updateState { copy(signInError = EmailSignInError.UNKNOWN_ERROR) }
        }
    }
}
