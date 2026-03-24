package com.linguaceleris.auth.impl.ui.signin

import com.linguaceleris.auth.impl.navigation.RegistrationNavKey
import com.linguaceleris.auth.impl.ui.signin.SignInEffect.SignInWithGoogle
import com.linguaceleris.navigation.Navigator
import com.linguaceleris.network.CredentialService
import com.linguaceleris.quizselection.api.QuizSelectionNavKey
import com.linguaceleris.ui.EffectViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class SignInViewModel @Inject constructor(
    private val navigator: Navigator,
    private val credentialService: CredentialService,
) : EffectViewModel<SignInUiState, SignInEvent, SignInEffect>(initialState = SignInUiState()) {

    override fun onEvent(event: SignInEvent) {
        when (event) {
            SignInEvent.OnEmailSignInClick -> {}

            SignInEvent.OnSignInAsGuestClick -> {}

            SignInEvent.OnGoogleSignInClick -> sendEffect(
                SignInWithGoogle(credentialService.getWebClientId()),
            )

            is SignInEvent.OnGoogleTokenReceived -> onGoogleTokenReceived(event.idToken)

            SignInEvent.OnRegistrationClick -> navigator.navigateTo(RegistrationNavKey)
        }
    }

    private fun onGoogleTokenReceived(idToken: String) {
        launch {
            credentialService.signInWithGoogle(idToken)
            navigator.replace(QuizSelectionNavKey)
        }
    }
}
