@file:PendingUiTests

package com.linguaceleris.auth.impl.ui.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.auth.impl.R
import com.linguaceleris.auth.impl.ui.widget.GoogleSignInButton
import com.linguaceleris.auth.impl.ui.widget.triggerGoogleSignIn
import com.linguaceleris.designsystem.widgets.ButtonDescription
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.LoadingScaffold
import com.linguaceleris.designsystem.widgets.ScreenPreviews
import com.linguaceleris.designsystem.widgets.ThreeButtonsDialog
import com.linguaceleris.designsystem.widgets.buttons.ButtonSize
import com.linguaceleris.designsystem.widgets.buttons.LCFilledButton
import com.linguaceleris.designsystem.widgets.buttons.LCFilledTonalButton
import com.linguaceleris.designsystem.widgets.buttons.LCTextButton
import com.linguaceleris.testing.PendingUiTests
import com.linguaceleris.ui.LocalSnackbarHostState

@Composable
internal fun SignInScreen(viewModel: SignInViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SignInEffect.SignInWithGoogle -> {
                    triggerGoogleSignIn(
                        context = context,
                        webClientId = effect.webClientId,
                        onTokenReceived = {
                            viewModel.onEvent(SignInEvent.OnGoogleTokenReceived(it))
                        },
                        onError = {
                            viewModel.onEvent(SignInEvent.OnGoogleGetCredentialException(it))
                        },
                    )
                }

                is SignInEffect.ShowSnackBarError -> {
                    snackbarHostState.showSnackbar(
                        message = effect.error.message.asString(context),
                        withDismissAction = true,
                    )
                }
            }
        }
    }

    SignInScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun SignInScreenContent(state: SignInUiState, onEvent: (SignInEvent) -> Unit) {
    LoadingScaffold(
        modifier = Modifier.fillMaxSize(),
        isLoading = state.isLoading,
    ) { paddingValues ->
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val buttonSize = if (maxHeight > 720.dp) {
                ButtonSize.MEDIUM
            } else {
                ButtonSize.SMALL
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Image(
                        modifier = Modifier
                            .weight(1f, fill = false)
                            .sizeIn(maxHeight = 320.dp, maxWidth = 240.dp)
                            .fillMaxSize()
                            .padding(16.dp),
                        painter = painterResource(
                            id = com.linguaceleris.designsystem.R.drawable.logo,
                        ),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp,
                            Alignment.CenterVertically,
                        ),
                    ) {
                        Text(
                            text = stringResource(R.string.auth_welcome_title),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            autoSize = TextAutoSize.StepBased(
                                maxFontSize = MaterialTheme.typography.displaySmall.fontSize,
                            ),
                        )

                        BasicText(
                            text = stringResource(R.string.auth_welcome_subtitle),
                            maxLines = 3,
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                textAlign = TextAlign.Center,
                            ),
                            autoSize = TextAutoSize.StepBased(
                                minFontSize = 10.sp,
                                maxFontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            ),
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    GoogleSignInButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onEvent(SignInEvent.OnGoogleSignInClick) },
                        buttonSize = buttonSize,
                    )

                    LCFilledButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.auth_email_sign_in),
                        icon = painterResource(com.linguaceleris.designsystem.R.drawable.email),
                        buttonSize = buttonSize,
                        onClick = { onEvent(SignInEvent.OnEmailSignInClick) },
                    )

                    LCFilledTonalButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.auth_registration),
                        buttonSize = buttonSize,
                        onClick = { onEvent(SignInEvent.OnRegistrationClick) },
                    )

                    LCTextButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.auth_guest_sign_in),
                        buttonSize = buttonSize,
                        onClick = { onEvent(SignInEvent.OnSignInAsGuestClick) },
                    )
                }
            }
        }
    }

    if (state.showAnonymousSignInDialog) {
        ThreeButtonsDialog(
            title = stringResource(R.string.auth_anonymous_dialog_title),
            description = stringResource(R.string.auth_anonymous_dialog_message),
            okButtonDescription = ButtonDescription(
                text = stringResource(R.string.auth_continue),
                onClick = { onEvent(SignInEvent.OnAnonymousSignInConfirmClick) },
            ),
            cancelButtonDescription = ButtonDescription(
                text = stringResource(R.string.auth_cancel),
                onClick = { onEvent(SignInEvent.OnAnonymousSignInCancelClick) },
            ),
            onDismissRequest = { onEvent(SignInEvent.OnAnonymousSignInCancelClick) },
        )
    }
}

@ScreenPreviews
@Composable
private fun SignInScreenPreview() {
    LCPreview {
        SignInScreenContent(SignInUiState()) {}
    }
}

@ScreenPreviews
@Composable
private fun SignInScreenLoadingPreview() {
    LCPreview {
        SignInScreenContent(SignInUiState(isLoading = true)) {}
    }
}

@ScreenPreviews
@Composable
private fun SignInScreenAnonymousPreview() {
    LCPreview {
        SignInScreenContent(SignInUiState(showAnonymousSignInDialog = true)) {}
    }
}
