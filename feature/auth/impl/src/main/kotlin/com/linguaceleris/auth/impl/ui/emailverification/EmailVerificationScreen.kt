@file:PendingUiTests

package com.linguaceleris.auth.impl.ui.emailverification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.auth.impl.R
import com.linguaceleris.auth.impl.ui.registration.openEmailApp
import com.linguaceleris.designsystem.widgets.ButtonDescription
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.LoadingScaffold
import com.linguaceleris.designsystem.widgets.ScreenPreviews
import com.linguaceleris.designsystem.widgets.ThreeButtonsDialog
import com.linguaceleris.designsystem.widgets.buttons.ButtonSize
import com.linguaceleris.designsystem.widgets.buttons.LCFilledButton
import com.linguaceleris.designsystem.widgets.buttons.LCOutlineButton
import com.linguaceleris.designsystem.widgets.buttons.LCTextButton
import com.linguaceleris.testing.PendingUiTests
import com.linguaceleris.ui.LocalSnackbarHostState

@Composable
internal fun EmailVerificationScreen(viewModel: EmailVerificationViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                EmailVerificationEffect.OpenEmail -> openEmailApp(context)

                is EmailVerificationEffect.ShowSnackbarError -> {
                    snackbarHostState.showSnackbar(
                        message = effect.error.message.asString(context),
                        withDismissAction = true,
                    )
                }
            }
        }
    }

    EmailVerificationScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun EmailVerificationScreenContent(
    state: EmailVerificationUiState,
    onEvent: (EmailVerificationEvent) -> Unit,
) {
    LoadingScaffold(
        modifier = Modifier.fillMaxSize(),
        isLoading = state.isLoading,
        loadingText = stringResource(R.string.auth_sending_verification_loading),
        title = stringResource(id = R.string.auth_email_verification_title),
        onNavigationButtonClick = { onEvent(EmailVerificationEvent.OnBackClicked) }.takeIf {
            state.navigationBackIsAvailable
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(
                    16.dp,
                    Alignment.CenterVertically,
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(state.verificationState.title),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.displaySmall,
                )

                Text(
                    text = stringResource(state.verificationState.message, state.email),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(
                    8.dp,
                    Alignment.CenterVertically,
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val rowAlpha =
                    if (state.verificationState ==
                        EmailVerificationState.SUCCESS
                    ) {
                        1f
                    } else {
                        0f
                    }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(rowAlpha),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 8.dp,
                        alignment = Alignment.CenterHorizontally,
                    ),
                ) {
                    LCOutlineButton(
                        text = stringResource(R.string.auth_open_mail),
                        buttonSize = ButtonSize.SMALL,
                        onClick = { onEvent(EmailVerificationEvent.OnOpenMailClicked) },
                    )

                    val sendAgainText = if (state.sendAgainEnable) {
                        stringResource(R.string.auth_send_again)
                    } else {
                        stringResource(R.string.auth_send_again_delay, state.sendAgainTimer)
                    }
                    LCOutlineButton(
                        modifier = Modifier.weight(1f),
                        text = sendAgainText,
                        isEnable = state.sendAgainEnable,
                        buttonSize = ButtonSize.SMALL,
                        onClick = { onEvent(EmailVerificationEvent.OnSendAgainClicked) },
                    )
                }

                if (state.verificationState == EmailVerificationState.SUCCESS) {
                    LCFilledButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.auth_continue),
                        buttonSize = ButtonSize.MEDIUM,
                        onClick = { onEvent(EmailVerificationEvent.OnContinueClicked) },
                    )
                } else {
                    LCFilledButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.auth_verify_email),
                        buttonSize = ButtonSize.MEDIUM,
                        onClick = { onEvent(EmailVerificationEvent.OnVerifyEmailClicked) },
                    )
                }

                LCTextButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(R.string.auth_exit),
                    onClick = { onEvent(EmailVerificationEvent.OnExitClicked) },
                )
            }
        }
        if (state.showEmailVerificationDialog) {
            ThreeButtonsDialog(
                title = stringResource(R.string.auth_email_verification_dialog_title),
                description = stringResource(
                    R.string.auth_email_verification_dialog_description,
                ),
                okButtonDescription = ButtonDescription(
                    text = stringResource(R.string.auth_ok),
                    onClick = {
                        onEvent(EmailVerificationEvent.OnHideEmailVerificationDialog)
                    },
                ),
                onDismissRequest = {
                    onEvent(EmailVerificationEvent.OnHideEmailVerificationDialog)
                },
            )
        }
    }
}

@ScreenPreviews
@Composable
private fun EmailVerificationScreenPreview() {
    LCPreview {
        EmailVerificationScreenContent(EmailVerificationUiState(email = "test@test.com")) {}
    }
}

@ScreenPreviews
@Composable
private fun EmailVerificationSuccessScreenPreview() {
    LCPreview {
        EmailVerificationScreenContent(
            EmailVerificationUiState(
                email = "test@test.com",
                verificationState = EmailVerificationState.SUCCESS,
            ),
        ) {
        }
    }
}
