package com.linguaceleris.auth.impl.ui.forgotpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.linguaceleris.designsystem.theme.extendedColors
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.LoadingScaffold
import com.linguaceleris.designsystem.widgets.ScreenPreviews
import com.linguaceleris.designsystem.widgets.buttons.ButtonSize
import com.linguaceleris.designsystem.widgets.buttons.LCFilledButton

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
    LoadingScaffold(
        modifier = Modifier.fillMaxSize(),
        isLoading = state.isLoading,
        loadingText = stringResource(R.string.auth_reset_password_loading),
        title = stringResource(id = R.string.auth_reset_password_title),
        onNavigationButtonClick = { onEvent(ForgotPasswordEvent.OnBackClicked) },
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
                    imeAction = ImeAction.Done,
                    onValueChange = { onEvent(ForgotPasswordEvent.OnEmailChanged(it)) },
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

@ScreenPreviews
@Composable
private fun ForgotPasswordScreenPreview() {
    LCPreview {
        ForgotPasswordScreenContent(ForgotPasswordUiState()) {}
    }
}

@ScreenPreviews
@Composable
private fun ForgotPasswordResetSuccessScreenPreview() {
    LCPreview {
        ForgotPasswordScreenContent(ForgotPasswordUiState(showSuccess = true)) {}
    }
}
