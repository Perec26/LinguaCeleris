package com.linguaceleris.auth.impl.ui.registration

import com.linguaceleris.auth.impl.domain.GetEmailVerificationUseCase
import com.linguaceleris.auth.impl.domain.RegisterUseCase
import com.linguaceleris.auth.impl.domain.SendEmailVerificationUseCase
import com.linguaceleris.auth.impl.domain.ValidateConfirmPasswordUseCase
import com.linguaceleris.auth.impl.domain.ValidateEmailUseCase
import com.linguaceleris.auth.impl.domain.ValidateNicknameUseCase
import com.linguaceleris.auth.impl.domain.ValidatePasswordUseCase
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quizselection.api.QuizSelectionNavKey
import com.linguaceleris.ui.EffectViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

private const val SEND_AGAIN_TIMER = 60

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
) : EffectViewModel<RegistrationUiState, RegistrationEvent, RegistrationEffect>(
    initialState = RegistrationUiState(),
) {

    override fun onEvent(event: RegistrationEvent) {
        when (event) {
            RegistrationEvent.OnBackClicked -> navigator.back()

            is RegistrationEvent.OnConfirmPasswordChanged -> {
                updateState { onConfirmPasswordChanged(event.confirmPassword) }
            }

            is RegistrationEvent.OnConfirmPasswordVisibilityChanged -> {
                updateState { onConfirmPasswordVisibilityChanged() }
            }

            is RegistrationEvent.OnEmailChanged -> {
                updateState { onEmailChanged(event.email) }
            }

            is RegistrationEvent.OnNickNameChanged -> {
                updateState { onNicknameChanged(event.nickName) }
            }

            is RegistrationEvent.OnPasswordChanged -> {
                updateState { onPasswordChanged(event.password) }
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
        if (getEmailVerificationUseCase()) {
            navigator.navigateTo(QuizSelectionNavKey)
        } else {
            updateState { onShowEmailVerificationDialog() }
        }
    }

    private fun onRegisterClicked() {
        val validationState = ValidationState(
            nickname = validateNicknameUseCase(state.value.nickname),
            email = validateEmailUseCase(state.value.email),
            password = validatePasswordUseCase(state.value.password),
            confirmPassword = validateConfirmPasswordUseCase(
                state.value.confirmPassword,
                state.value.password,
            ),
        )

        if (!validationState.isSuccessful) {
            updateState { onValidationStateChanged(validationState) }
            return
        }

        launch {
            updateState { onRegistrationStarted() }
            registerUseCase(currentState.nickname, currentState.email, currentState.password)
            updateState { onRegistrationFinished() }
            startSendAgainTimer()
        }
    }

    private fun onSendAgain() {
        launch {
            updateState { onSendingVerificationStarted() }
            sendEmailVerificationUseCase()
            updateState { onSendingVerificationFinished() }
            startSendAgainTimer()
        }
    }

    private fun startSendAgainTimer() {
        launch {
            var timerValue = SEND_AGAIN_TIMER
            updateState { setSendAgainTimer(timerValue) }
            while (timerValue > 0) {
                delay(1000)
                timerValue -= 1
                updateState { setSendAgainTimer(timerValue) }
            }
        }
    }
}
