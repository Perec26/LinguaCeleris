package com.linguaceleris.auth.impl.ui.email

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.auth.impl.R
import com.linguaceleris.auth.impl.ui.widget.EmailTextField
import com.linguaceleris.auth.impl.ui.widget.PasswordTextField
import com.linguaceleris.designsystem.widgets.ButtonSize
import com.linguaceleris.designsystem.widgets.LCFilledButton
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.LCTextButton
import com.linguaceleris.designsystem.widgets.LoadingScaffold
import com.linguaceleris.designsystem.widgets.ScreenPreviews

@Composable
internal fun EmailSignInScreen(viewModel: EmailSignInViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    EmailSignInScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun EmailSignInScreenContent(
    state: EmailSignInUiState,
    onEvent: (EmailSignInEvent) -> Unit,
) {
    LoadingScaffold(
        modifier = Modifier.fillMaxSize(),
        isLoading = state.isLoading,
        loadingText = stringResource(R.string.auth_email_enter),
        title = stringResource(id = R.string.auth_email_enter_title),
        onNavigationButtonClick = { onEvent(EmailSignInEvent.OnBackClicked) },
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
                EmailTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.email,
                    validationResult = state.validationState.email,
                    imeAction = ImeAction.Next,
                    onValueChange = { onEvent(EmailSignInEvent.OnEmailChanged(it)) },
                )

                PasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.password,
                    validationResult = state.validationState.password,
                    isPasswordVisible = state.isPasswordVisible,
                    imeAction = ImeAction.Done,
                    onValueChange = { onEvent(EmailSignInEvent.OnPasswordChanged(it)) },
                    onVisibilityClick = { onEvent(EmailSignInEvent.OnPasswordVisibilityChanged) },
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    LCTextButton(
                        text = stringResource(R.string.auth_forgot_password),
                        onClick = { onEvent(EmailSignInEvent.OnForgotPasswordClicked) },

                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    if (state.signInError != null) {
                        Text(
                            text = stringResource(state.signInError.message),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }

            LCFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = stringResource(R.string.auth_enter),
                buttonSize = ButtonSize.MEDIUM,
                onClick = { onEvent(EmailSignInEvent.OnEnterClick) },
            )
        }
    }
}

@ScreenPreviews
@Composable
private fun EmailSignInScreenPreview() {
    LCPreview {
        EmailSignInScreenContent(EmailSignInUiState()) {}
    }
}
