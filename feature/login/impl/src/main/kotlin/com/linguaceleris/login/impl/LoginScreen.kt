package com.linguaceleris.login.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme
import com.linguaceleris.designsystem.widgets.ScreenPreviews

@Composable
internal fun LoginScreen(viewModel: LoginViewModel) {
    val state = viewModel.state.collectAsState().value

    LoginScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun LoginScreenContent(state: LoginUiState, onEvent: (LoginEvent) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = state.number.toString())
    }
}

@ScreenPreviews
@Composable
private fun LoginScreenPreview() {
    LinguaCelerisTheme {
        LoginScreenContent(LoginUiState(6)) {}
    }
}
