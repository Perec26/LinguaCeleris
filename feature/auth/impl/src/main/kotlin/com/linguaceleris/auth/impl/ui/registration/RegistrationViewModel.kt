package com.linguaceleris.auth.impl.ui.registration

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.linguaceleris.auth.impl.domain.GetEmailVerificationUseCase
import com.linguaceleris.auth.impl.domain.GetSendAgainTimerUseCase
import com.linguaceleris.auth.impl.domain.RegisterUseCase
import com.linguaceleris.auth.impl.domain.SendEmailVerificationUseCase
import com.linguaceleris.auth.impl.domain.ValidateConfirmPasswordUseCase
import com.linguaceleris.auth.impl.domain.ValidateEmailUseCase
import com.linguaceleris.auth.impl.domain.ValidateNicknameUseCase
import com.linguaceleris.auth.impl.domain.ValidatePasswordUseCase
import com.linguaceleris.auth.impl.ui.VerificationSnackbarError
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quizselection.api.QuizSelectionNavKey
import com.linguaceleris.ui.EffectViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class RegistrationViewModel @Inject constructor(
    private val navigator: Navigator,
    private val validateNicknameUseCase: ValidateNicknameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    private val registerUseCase: RegisterUseCase,
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    private val getEmailVerificationUseCase: GetEmailVerificationUseCase,
    private val getSendAgainTimerUseCase: GetSendAgainTimerUseCase,
) : EffectViewModel<RegistrationUiState, RegistrationEvent, RegistrationEffect>(
    initialState = RegistrationUiState(),
) {

    override fun onEvent(event: RegistrationEvent) {
        when (event) {
            RegistrationEvent.OnBackClicked -> navigator.back()

            is RegistrationEvent.OnNickNameChanged -> {
                updateState { onNicknameChanged(event.nickName) }
            }

            is RegistrationEvent.OnEmailChanged -> {
                updateState { onEmailChanged(event.email) }
            }

            is RegistrationEvent.OnPasswordChanged -> {
                updateState { onPasswordChanged(event.password) }
            }

            is RegistrationEvent.OnConfirmPasswordChanged -> {
                updateState { onConfirmPasswordChanged(event.confirmPassword) }
            }

            is RegistrationEvent.OnConfirmPasswordVisibilityChanged -> {
                updateState { onConfirmPasswordVisibilityChanged() }
            }

            is RegistrationEvent.OnPasswordVisibilityChanged -> {
                updateState { onPasswordVisibilityChanged() }
            }

            RegistrationEvent.OnRegisterClicked -> onRegisterClicked()

            RegistrationEvent.OnOpenMailClicked -> sendEffect(RegistrationEffect.OpenEmail)

            RegistrationEvent.OnSendAgainClicked -> onSendAgain()

            RegistrationEvent.OnContinueClicked -> onContinueClicked()

            RegistrationEvent.OnHideEmailVerificationDialog -> {
                updateState { onHideEmailVerificationDialog() }
            }
        }
    }

    private fun onContinueClicked() {
        launch(::handleEmailVerificationError) {
            if (getEmailVerificationUseCase()) {
                navigator.startWith(QuizSelectionNavKey)
            } else {
                updateState { onShowEmailVerificationDialog() }
            }
        }
    }

    private fun handleEmailVerificationError(exception: Exception) {
        sendEffect(RegistrationEffect.ShowSnackbarError(VerificationSnackbarError.EMAIL))
    }

    private fun onRegisterClicked() {
        val validationState = state.value.run {
            RegistrationValidationState(
                nickname = validateNicknameUseCase(nickname),
                email = validateEmailUseCase(email),
                password = validatePasswordUseCase(password),
                confirmPassword = validateConfirmPasswordUseCase(confirmPassword, password),
            )
        }

        if (!validationState.isSuccessful) {
            updateState { onValidationStateChanged(validationState) }
            return
        }

        updateState { onRegistrationStarted() }

        launch(::handleRegistrationError) {
            registerUseCase(currentState.nickname, currentState.email, currentState.password)
            updateState { onRegistrationFinished(RegistrationState.SUCCESS) }
            startSendAgainTimer()
        }
    }

    private fun handleRegistrationError(exception: Exception) {
        val registrationState = when (exception) {
            is FirebaseAuthUserCollisionException -> RegistrationState.USER_EXIST
            is FirebaseAuthWeakPasswordException -> RegistrationState.WEAK_PASSWORD
            is FirebaseAuthInvalidCredentialsException -> RegistrationState.INVALID_CREDENTIALS
            else -> RegistrationState.UNKNOWN_ERROR
        }
        updateState { onRegistrationFinished(registrationState) }
    }

    private fun onSendAgain() {
        updateState { onSendingVerificationStarted() }
        launch(
            onError = ::handleSendingVerificationError,
            doFinally = {
                updateState { onSendingVerificationFinished() }
                startSendAgainTimer()
            },
        ) {
            sendEmailVerificationUseCase()
        }
    }

    private fun handleSendingVerificationError(exception: Exception) {
        sendEffect(RegistrationEffect.ShowSnackbarError(VerificationSnackbarError.SENDING))
    }

    private fun startSendAgainTimer() {
        launch {
            getSendAgainTimerUseCase().collect { updateState { setSendAgainTimer(it) } }
        }
    }
}
