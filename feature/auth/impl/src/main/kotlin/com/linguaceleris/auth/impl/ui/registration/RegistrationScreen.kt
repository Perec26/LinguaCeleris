@file:PendingUiTests

package com.linguaceleris.auth.impl.ui.registration

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.auth.impl.R
import com.linguaceleris.auth.impl.ui.widget.ConfirmPasswordTextField
import com.linguaceleris.auth.impl.ui.widget.EmailTextField
import com.linguaceleris.auth.impl.ui.widget.NicknameTextField
import com.linguaceleris.auth.impl.ui.widget.PasswordTextField
import com.linguaceleris.designsystem.theme.extendedColors
import com.linguaceleris.designsystem.widgets.ButtonDescription
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.LoadingScaffold
import com.linguaceleris.designsystem.widgets.ScreenPreviews
import com.linguaceleris.designsystem.widgets.ThreeButtonsDialog
import com.linguaceleris.designsystem.widgets.buttons.ButtonSize
import com.linguaceleris.designsystem.widgets.buttons.LCFilledButton
import com.linguaceleris.designsystem.widgets.buttons.LCOutlineButton
import com.linguaceleris.testing.PendingUiTests
import com.linguaceleris.ui.LocalSnackbarHostState

@Composable
internal fun RegistrationScreen(viewModel: RegistrationViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is RegistrationEffect.OpenEmail -> openEmailApp(context)

                is RegistrationEffect.ShowSnackbarError -> {
                    snackbarHostState.showSnackbar(
                        message = effect.error.message.asString(context),
                        withDismissAction = true,
                    )
                }
            }
        }
    }

    RegistrationScreenContent(state, viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegistrationScreenContent(
    state: RegistrationUiState,
    onEvent: (RegistrationEvent) -> Unit,
) {
    val loadingText = when {
        state.isRegistrationInProgress -> stringResource(R.string.auth_registration_loading)

        state.isSendingVerificationInProgress -> {
            stringResource(R.string.auth_sending_verification_loading)
        }

        else -> stringResource(R.string.auth_loading)
    }
    LoadingScaffold(
        modifier = Modifier.fillMaxSize(),
        isLoading = state.isRegistrationInProgress || state.isSendingVerificationInProgress,
        loadingText = loadingText,
        title = stringResource(id = R.string.auth_registration),
        onNavigationButtonClick = { onEvent(RegistrationEvent.OnBackClicked) },
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
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                NicknameTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.nickname,
                    validationResult = state.validationState.nickname,
                    imeAction = ImeAction.Next,
                    onValueChange = { onEvent(RegistrationEvent.OnNickNameChanged(it)) },
                )

                EmailTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.email,
                    validationResult = state.validationState.email,
                    imeAction = ImeAction.Next,
                    onValueChange = { onEvent(RegistrationEvent.OnEmailChanged(it)) },
                )

                PasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.password,
                    validationResult = state.validationState.password,
                    isPasswordVisible = state.isPasswordVisible,
                    imeAction = ImeAction.Next,
                    onValueChange = { onEvent(RegistrationEvent.OnPasswordChanged(it)) },
                    onVisibilityClick = { onEvent(RegistrationEvent.OnPasswordVisibilityChanged) },
                )

                ConfirmPasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.confirmPassword,
                    validationResult = state.validationState.confirmPassword,
                    isPasswordVisible = state.isConfirmPasswordVisible,
                    imeAction = ImeAction.Done,
                    onValueChange = {
                        onEvent(RegistrationEvent.OnConfirmPasswordChanged(it))
                    },
                    onVisibilityClick = {
                        onEvent(RegistrationEvent.OnConfirmPasswordVisibilityChanged)
                    },
                )

                val registrationStateColor =
                    if (state.registrationState == RegistrationState.SUCCESS) {
                        MaterialTheme.extendedColors.green.color
                    } else {
                        MaterialTheme.colorScheme.error
                    }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    state.registrationState?.let {
                        Text(
                            text = stringResource(
                                state.registrationState.message,
                                state.email,
                            ),
                            textAlign = TextAlign.Center,
                            color = registrationStateColor,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
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
                    if (state.registrationState == RegistrationState.SUCCESS) 1f else 0f
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
                        onClick = { onEvent(RegistrationEvent.OnOpenMailClicked) },
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
                        onClick = { onEvent(RegistrationEvent.OnSendAgainClicked) },
                    )
                }

                if (state.registrationState == RegistrationState.SUCCESS) {
                    LCFilledButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.auth_continue),
                        buttonSize = ButtonSize.MEDIUM,
                        onClick = { onEvent(RegistrationEvent.OnContinueClicked) },
                    )
                } else {
                    LCFilledButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.auth_register),
                        buttonSize = ButtonSize.MEDIUM,
                        onClick = { onEvent(RegistrationEvent.OnRegisterClicked) },
                    )
                }
            }
        }
    }

    if (state.showEmailVerificationDialog) {
        ThreeButtonsDialog(
            title = stringResource(R.string.auth_email_verification_dialog_title),
            description = stringResource(R.string.auth_email_verification_dialog_description),
            okButtonDescription = ButtonDescription(
                text = stringResource(R.string.auth_ok),
                onClick = { onEvent(RegistrationEvent.OnHideEmailVerificationDialog) },
            ),
            onDismissRequest = { onEvent(RegistrationEvent.OnHideEmailVerificationDialog) },
        )
    }
}

fun openEmailApp(context: Context) {
    val intent = Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_APP_EMAIL)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    context.startActivity(intent)
}

@ScreenPreviews
@Composable
private fun RegistrationScreenPreview() {
    LCPreview {
        RegistrationScreenContent(RegistrationUiState()) {}
    }
}

@ScreenPreviews
@Composable
private fun RegistrationSuccessScreenPreview() {
    LCPreview {
        RegistrationScreenContent(
            RegistrationUiState(registrationState = RegistrationState.SUCCESS),
        ) {}
    }
}

@ScreenPreviews
@Composable
private fun RegistrationLoadingScreenPreview() {
    LCPreview {
        RegistrationScreenContent(RegistrationUiState(isRegistrationInProgress = true)) {}
    }
}

@ScreenPreviews
@Composable
private fun RegistrationScreenWithDialogPreview() {
    LCPreview {
        RegistrationScreenContent(RegistrationUiState(showEmailVerificationDialog = true)) {}
    }
}
