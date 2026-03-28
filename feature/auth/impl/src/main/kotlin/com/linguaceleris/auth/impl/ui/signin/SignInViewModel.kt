package com.linguaceleris.auth.impl.ui.signin

import androidx.credentials.exceptions.GetCredentialCancellationException
import com.linguaceleris.auth.impl.domain.GetWebClientIdUseCase
import com.linguaceleris.auth.impl.domain.SignInAnonymouslyUseCase
import com.linguaceleris.auth.impl.domain.SignInWithGoogleUseCase
import com.linguaceleris.auth.impl.navigation.EmailSignInNavKey
import com.linguaceleris.auth.impl.navigation.RegistrationNavKey
import com.linguaceleris.auth.impl.ui.signin.SignInEffect.SignInWithGoogle
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.quizselection.api.QuizSelectionNavKey
import com.linguaceleris.ui.EffectViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class SignInViewModel @Inject constructor(
    private val navigator: Navigator,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val getWebClientIdUseCase: GetWebClientIdUseCase,
    private val signInAnonymouslyUseCase: SignInAnonymouslyUseCase,
) : EffectViewModel<SignInUiState, SignInEvent, SignInEffect>(initialState = SignInUiState()) {

    override fun onEvent(event: SignInEvent) {
        when (event) {
            SignInEvent.OnEmailSignInClick -> navigator.navigateTo(EmailSignInNavKey)

            SignInEvent.OnSignInAsGuestClick -> updateState { showAnonymousSignInDialog() }

            SignInEvent.OnGoogleSignInClick -> onGoogleSignInClick()

            is SignInEvent.OnGoogleTokenReceived -> onGoogleTokenReceived(event.idToken)

            SignInEvent.OnRegistrationClick -> navigator.navigateTo(RegistrationNavKey)

            SignInEvent.OnAnonymousSignInConfirmClick -> onAnonymousSignInConfirmClick()

            SignInEvent.OnAnonymousSignInCancelClick -> updateState { hideAnonymousSignInDialog() }

            is SignInEvent.OnGoogleGetCredentialException -> onGoogleGetCredentialException(
                event.exception,
            )
        }
    }

    private fun onGoogleGetCredentialException(exception: Exception) {
        updateState { hideLoading() }
        if (exception !is GetCredentialCancellationException) {
            sendEffect(SignInEffect.SnackBarError(SignInError.SIGN_IN_WITH_GOOGLE_ERROR))
        }
    }

    private fun onGoogleSignInClick() {
        updateState { showLoading() }
        sendEffect(SignInWithGoogle(getWebClientIdUseCase()))
    }

    private fun onAnonymousSignInConfirmClick() {
        launch(
            onError = {
                updateState { hideLoading() }
                sendEffect(SignInEffect.SnackBarError(SignInError.SIGN_IN_WITH_ANONYMOUSLY_ERROR))
            },
        ) {
            updateState { hideAnonymousSignInDialog() }
            updateState { showLoading() }
            signInAnonymouslyUseCase()
            navigator.startWith(QuizSelectionNavKey)
        }
    }

    private fun onGoogleTokenReceived(idToken: String) {
        launch(
            onError = {
                updateState { hideLoading() }
                sendEffect(SignInEffect.SnackBarError(SignInError.SIGN_IN_WITH_GOOGLE_ERROR))
            },
        ) {
            updateState { showLoading() }
            signInWithGoogleUseCase(idToken)
            navigator.startWith(QuizSelectionNavKey)
        }
    }
}
