package com.linguaceleris.auth.impl.ui.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.linguaceleris.auth.impl.R
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme
import com.linguaceleris.designsystem.widgets.ButtonSize
import com.linguaceleris.designsystem.widgets.LCFilledButton
import com.linguaceleris.designsystem.widgets.LCFilledTonalButton
import com.linguaceleris.designsystem.widgets.LCTextButton
import com.linguaceleris.designsystem.widgets.ScreenPreviews
import com.linguaceleris.designsystem.widgets.SpacerHeight
import kotlinx.coroutines.launch

@Composable
internal fun SignInScreen(viewModel: SignInViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    // TODO: переписать
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SignInEffect.SignInWithGoogle -> {
                    launch {
                        try {
                            val credentialManager = CredentialManager.create(context)

                            val googleIdOption = GetGoogleIdOption.Builder()
                                .setServerClientId(effect.webClientId)
                                .setFilterByAuthorizedAccounts(false)
                                .build()

                            val request = GetCredentialRequest.Builder()
                                .addCredentialOption(googleIdOption)
                                .build()

                            val response = credentialManager.getCredential(
                                context = context,
                                request = request,
                            )

                            val googleIdTokenCredential = response.credential
                            val idToken =
                                GoogleIdTokenCredential.createFrom(
                                    googleIdTokenCredential.data,
                                ).idToken
                            viewModel.onEvent(SignInEvent.OnGoogleTokenReceived(idToken))
                        } catch (exception: GetCredentialException) {
                            val k = exception
                        }
                    }
                }
            }
        }
    }

    SignInScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun SignInScreenContent(state: SignInUiState, onEvent: (SignInEvent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Image(
            modifier = Modifier.size(240.dp),
            painter = painterResource(id = com.linguaceleris.designsystem.R.drawable.logo),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text(
                text = stringResource(R.string.auth_welcome_title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurface,
            )

            SpacerHeight(16.dp)

            Text(
                text = stringResource(R.string.auth_welcome_subtitle),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,

            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            LCFilledButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.auth_google_sign_in),
                icon = painterResource(com.linguaceleris.designsystem.R.drawable.add),
                buttonSize = ButtonSize.MEDIUM,
                onClick = { onEvent(SignInEvent.OnGoogleSignInClick) },
            )

            LCFilledButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.auth_email_sign_in),
                icon = painterResource(com.linguaceleris.designsystem.R.drawable.email),
                buttonSize = ButtonSize.MEDIUM,
                onClick = { onEvent(SignInEvent.OnEmailSignInClick) },
            )

            LCFilledTonalButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.auth_registration),
                buttonSize = ButtonSize.MEDIUM,
                onClick = { onEvent(SignInEvent.OnRegistrationClick) },
            )

            LCTextButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.auth_guest_sign_in),
                buttonSize = ButtonSize.MEDIUM,
                onClick = { onEvent(SignInEvent.OnSignInAsGuestClick) },
            )
        }
    }
}

@ScreenPreviews
@Composable
private fun SignInScreenPreview() {
    LinguaCelerisTheme {
        Surface {
            SignInScreenContent(SignInUiState()) {}
        }
    }
}
