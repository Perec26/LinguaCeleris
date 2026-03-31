package com.linguaceleris.auth.impl.ui.emailverification

import com.linguaceleris.auth.api.startWithSignIn
import com.linguaceleris.auth.impl.domain.GetEmailVerificationUseCase
import com.linguaceleris.auth.impl.domain.GetSendAgainTimerUseCase
import com.linguaceleris.auth.impl.domain.SendEmailVerificationUseCase
import com.linguaceleris.auth.impl.domain.SignOutUseCase
import com.linguaceleris.auth.impl.ui.VerificationSnackbarError
import com.linguaceleris.home.api.startWithHome
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.ui.EffectViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = EmailVerificationViewModel.Factory::class)
internal class EmailVerificationViewModel @AssistedInject constructor(
    private val navigator: Navigator,
    private val getEmailVerificationUseCase: GetEmailVerificationUseCase,
    private val singOutUseCase: SignOutUseCase,
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    private val getSendAgainTimerUseCase: GetSendAgainTimerUseCase,
    @Assisted private val fromStart: Boolean,
) : EffectViewModel<EmailVerificationUiState, EmailVerificationEvent, EmailVerificationEffect>(
    initialState = EmailVerificationUiState(navigationBackIsAvailable = !fromStart),
) {

    override fun onEvent(event: EmailVerificationEvent) {
        when (event) {
            EmailVerificationEvent.OnBackClicked -> navigator.back()

            EmailVerificationEvent.OnExitClicked -> onExitClicked()

            EmailVerificationEvent.OnOpenMailClicked -> sendEffect(
                EmailVerificationEffect.OpenEmail,
            )

            EmailVerificationEvent.OnSendAgainClicked -> onSendAgain()

            EmailVerificationEvent.OnVerifyEmailClicked -> onVerifyEmailClicked()

            EmailVerificationEvent.OnContinueClicked -> onContinueClicked()

            EmailVerificationEvent.OnHideEmailVerificationDialog -> updateState {
                onHideEmailVerificationDialog()
            }
        }
    }

    private fun onVerifyEmailClicked() {
        updateState { onSendingVerificationStarted() }

        launch(
            onError = ::handleSendingVerificationError,
            doFinally = {
                updateState { onSendingVerificationFinished() }
                startSendAgainTimer()
            },
        ) {
            sendEmailVerificationUseCase()
            updateState { onVerificationStateChanged(EmailVerificationState.SUCCESS) }
        }
    }

    private fun onExitClicked() {
        singOutUseCase()
        navigator.startWithSignIn()
    }

    private fun onContinueClicked() {
        launch(::handleEmailVerificationError) {
            if (getEmailVerificationUseCase()) {
                navigator.startWithHome()
            } else {
                updateState { onShowEmailVerificationDialog() }
            }
        }
    }

    private fun handleEmailVerificationError(exception: Exception) {
        sendEffect(EmailVerificationEffect.ShowSnackbarError(VerificationSnackbarError.EMAIL))
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
        sendEffect(EmailVerificationEffect.ShowSnackbarError(VerificationSnackbarError.SENDING))
    }

    private fun startSendAgainTimer() {
        launch {
            getSendAgainTimerUseCase().collect { updateState { setSendAgainTimer(it) } }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(fromStart: Boolean): EmailVerificationViewModel
    }
}
