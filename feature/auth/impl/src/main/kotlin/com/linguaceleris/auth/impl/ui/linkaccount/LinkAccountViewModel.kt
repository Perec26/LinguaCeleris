package com.linguaceleris.auth.impl.ui.linkaccount

import androidx.credentials.exceptions.GetCredentialCancellationException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.linguaceleris.auth.impl.domain.GetWebClientIdUseCase
import com.linguaceleris.auth.impl.domain.LinkWithGoogleUseCase
import com.linguaceleris.auth.impl.ui.model.SignInWithGoogleError
import com.linguaceleris.auth.impl.ui.model.SignInWithGoogleUserCollisionError
import com.linguaceleris.home.api.startWithHome
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.ui.EffectViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class LinkAccountViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getWebClientIdUseCase: GetWebClientIdUseCase,
    private val linkWithGoogleUseCase: LinkWithGoogleUseCase,
) : EffectViewModel<LinkAccountUiState, LinkAccountEvent, LinkAccountEffect>(
    initialState = LinkAccountUiState(),
) {

    override fun onEvent(event: LinkAccountEvent) {
        when (event) {
            LinkAccountEvent.OnBackClicked -> navigator.back()

            is LinkAccountEvent.OnGoogleGetCredentialException -> {
                onGoogleGetCredentialException(event.exception)
            }

            LinkAccountEvent.OnGoogleSignInClick -> onGoogleSignInClick()

            is LinkAccountEvent.OnGoogleTokenReceived -> onGoogleTokenReceived(event.idToken)
        }
    }

    private fun onGoogleSignInClick() {
        updateState { showLoading() }
        sendEffect(LinkAccountEffect.SignInWithGoogle(getWebClientIdUseCase()))
    }

    private fun onGoogleGetCredentialException(exception: Exception) {
        updateState { hideLoading() }
        if (exception !is GetCredentialCancellationException) {
            sendEffect(LinkAccountEffect.ShowSnackBarError(SignInWithGoogleError))
        }
    }

    private fun onGoogleTokenReceived(idToken: String) {
        updateState { showLoading() }
        launch(
            onError = ::handleGoogleSignInError,
        ) {
            linkWithGoogleUseCase(idToken)
            navigator.startWithHome()
        }
    }

    private fun handleGoogleSignInError(exception: Exception) {
        updateState { hideLoading() }
        val snackBarError = when (exception) {
            is FirebaseAuthUserCollisionException -> SignInWithGoogleUserCollisionError
            else -> SignInWithGoogleError
        }
        sendEffect(LinkAccountEffect.ShowSnackBarError(snackBarError))
    }
}
