package com.linguaceleris.auth.impl.ui.forgotpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.auth.impl.R
import com.linguaceleris.auth.impl.ui.registration.model.AuthValidationResult
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme
import com.linguaceleris.designsystem.theme.extendedColors
import com.linguaceleris.designsystem.widgets.ButtonSize
import com.linguaceleris.designsystem.widgets.CustomTopAppBar
import com.linguaceleris.designsystem.widgets.LCFilledButton
import com.linguaceleris.designsystem.widgets.LoadingWrapper
import com.linguaceleris.designsystem.widgets.ScreenPreviews

@Composable
internal fun ForgotPasswordScreen(viewModel: ForgotPasswordViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    ForgotPasswordScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun ForgotPasswordScreenContent(
    state: ForgotPasswordUiState,
    onEvent: (ForgotPasswordEvent) -> Unit,
) {
    LoadingWrapper(
        modifier = Modifier.fillMaxSize(),
        isLoading = state.isLoading,
        text = stringResource(R.string.auth_reset_password_loading),
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
                        value = state.email,
                        label = { Text(stringResource(R.string.auth_email)) },
                        placeholder = { Text(stringResource(R.string.auth_type_email)) },
                        supportingText = { SupportText(state.validationState.email) },
                        isError = state.validationState.email.isError,
                        singleLine = true,
                        onValueChange = { onEvent(ForgotPasswordEvent.OnEmailChanged(it)) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                        ),
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (state.showSuccess) {
                            Text(
                                text = stringResource(
                                    R.string.auth_reset_password_success_message,
                                    state.email,
                                ),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.extendedColors.green.color,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }

                LCFilledButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(R.string.auth_reset_password),
                    buttonSize = ButtonSize.MEDIUM,
                    isEnable = !state.showSuccess,
                    onClick = { onEvent(ForgotPasswordEvent.OnResetClick) },
                )
            }
        }
    }
}

@Composable
private fun TopBar(onEvent: (ForgotPasswordEvent) -> Unit) {
    CustomTopAppBar(
        title = stringResource(id = R.string.auth_reset_password_title),
        onNavigationClick = { onEvent(ForgotPasswordEvent.OnBackClicked) },
    )
}

@Composable
private fun SupportText(validationResult: AuthValidationResult) {
    if (validationResult is AuthValidationResult.Error) {
        Text(
            text = stringResource(validationResult.messageResId),
        )
    }
}

@ScreenPreviews
@Composable
private fun ForgotPasswordScreenPreview() {
    LinguaCelerisTheme {
        Surface {
            ForgotPasswordScreenContent(ForgotPasswordUiState()) {}
        }
    }
}

@ScreenPreviews
@Composable
private fun ForgotPasswordResetSuccessScreenPreview() {
    LinguaCelerisTheme {
        Surface {
            ForgotPasswordScreenContent(ForgotPasswordUiState(showSuccess = true)) {}
        }
    }
}
