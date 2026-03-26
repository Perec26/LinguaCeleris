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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.auth.impl.R
import com.linguaceleris.auth.impl.ui.registration.model.AuthValidationResult
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme
import com.linguaceleris.designsystem.theme.extendedColors
import com.linguaceleris.designsystem.widgets.ButtonDescription
import com.linguaceleris.designsystem.widgets.ButtonSize
import com.linguaceleris.designsystem.widgets.CustomTopAppBar
import com.linguaceleris.designsystem.widgets.LCFilledButton
import com.linguaceleris.designsystem.widgets.LCOutlineButton
import com.linguaceleris.designsystem.widgets.LoadingWrapper
import com.linguaceleris.designsystem.widgets.ScreenPreviews
import com.linguaceleris.designsystem.widgets.ThreeButtonsDialog
import com.linguaceleris.ui.LocalSnackbarHostState

@Composable
internal fun RegistrationScreen(viewModel: RegistrationViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
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
    LoadingWrapper(
        modifier = Modifier.fillMaxSize(),
        isLoading = state.isRegistrationInProgress || state.isSendingVerificationInProgress,
        text = loadingText,
    ) {
        Scaffold(
            topBar = { TopBar(onEvent) },
            containerColor = Color.Transparent,
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
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.nickname,
                        label = { Text(stringResource(R.string.auth_nickname)) },
                        placeholder = { Text(stringResource(R.string.auth_enter_nickname)) },
                        supportingText = { SupportText(state.validationState.nickname) },
                        isError = state.validationState.nickname.isError,
                        singleLine = true,
                        onValueChange = { onEvent(RegistrationEvent.OnNickNameChanged(it)) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Next,
                        ),
                    )

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.email,
                        label = { Text(stringResource(R.string.auth_email)) },
                        placeholder = { Text(stringResource(R.string.auth_enter)) },
                        supportingText = { SupportText(state.validationState.email) },
                        isError = state.validationState.email.isError,
                        singleLine = true,
                        onValueChange = { onEvent(RegistrationEvent.OnEmailChanged(it)) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                        ),
                    )

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.password,
                        label = { Text(stringResource(R.string.auth_password)) },
                        placeholder = { Text(stringResource(R.string.auth_enter_password)) },
                        supportingText = { SupportText(state.validationState.password) },
                        singleLine = true,
                        isError = state.validationState.password.isError,
                        visualTransformation = getVisualTransformation(state.isPasswordVisible),
                        trailingIcon = {
                            VisibilityIcon(
                                state.isPasswordVisible,
                                onClick = {
                                    onEvent(RegistrationEvent.OnPasswordVisibilityChanged)
                                },
                            )
                        },
                        onValueChange = { onEvent(RegistrationEvent.OnPasswordChanged(it)) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next,
                        ),
                    )

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.confirmPassword,
                        label = { Text(stringResource(R.string.auth_confirm_password)) },
                        placeholder = {
                            Text(stringResource(R.string.auth_enter_confirm_password))
                        },
                        supportingText = { SupportText(state.validationState.confirmPassword) },
                        singleLine = true,
                        isError = state.validationState.confirmPassword.isError,
                        visualTransformation = getVisualTransformation(
                            state.isConfirmPasswordVisible,
                        ),
                        trailingIcon = {
                            VisibilityIcon(
                                state.isConfirmPasswordVisible,
                                onClick = {
                                    onEvent(RegistrationEvent.OnConfirmPasswordVisibilityChanged)
                                },
                            )
                        },
                        onValueChange = { onEvent(RegistrationEvent.OnConfirmPasswordChanged(it)) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done,
                        ),
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
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
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
}

@Composable
private fun TopBar(onEvent: (RegistrationEvent) -> Unit) {
    CustomTopAppBar(
        title = stringResource(id = R.string.auth_registration),
        onNavigationClick = { onEvent(RegistrationEvent.OnBackClicked) },
    )
}

private fun getVisualTransformation(isVisible: Boolean): VisualTransformation = if (isVisible) {
    VisualTransformation.None
} else {
    PasswordVisualTransformation()
}

@Composable
private fun SupportText(validationResult: AuthValidationResult) {
    if (validationResult is AuthValidationResult.Error) {
        Text(
            text = stringResource(validationResult.messageResId),
        )
    }
}

@Composable
private fun VisibilityIcon(isVisible: Boolean, onClick: () -> Unit) {
    val icon = if (isVisible) {
        painterResource(id = R.drawable.auth_visibility_off)
    } else {
        painterResource(id = R.drawable.auth_visibility)
    }

    IconButton(onClick = onClick) {
        Icon(painter = icon, contentDescription = null)
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
    LinguaCelerisTheme {
        Surface {
            RegistrationScreenContent(RegistrationUiState()) {}
        }
    }
}

@ScreenPreviews
@Composable
private fun RegistrationSuccessScreenPreview() {
    LinguaCelerisTheme {
        Surface {
            RegistrationScreenContent(
                RegistrationUiState(registrationState = RegistrationState.USER_EXIST),
            ) {
            }
        }
    }
}

@ScreenPreviews
@Composable
private fun RegistrationLoadingScreenPreview() {
    LinguaCelerisTheme {
        Surface {
            RegistrationScreenContent(RegistrationUiState(isRegistrationInProgress = true)) {}
        }
    }
}

@ScreenPreviews
@Composable
private fun RegistrationScreenWithDialogPreview() {
    LinguaCelerisTheme {
        Surface {
            RegistrationScreenContent(RegistrationUiState(showEmailVerificationDialog = true)) {}
        }
    }
}
