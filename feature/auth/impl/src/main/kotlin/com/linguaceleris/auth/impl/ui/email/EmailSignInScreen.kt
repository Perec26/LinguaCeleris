package com.linguaceleris.auth.impl.ui.email

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme
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
    Column {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = state.email,
            onValueChange = { onEvent(EmailSignInEvent.OnEmailChange(it)) },
            label = { Text("Email") },
        )

        TextField(
            value = state.email,
            onValueChange = { onEvent(EmailSignInEvent.OnEmailChange(it)) },
            label = { Text("Email") },
        )
    }
}

@ScreenPreviews
@Composable
private fun EmailSignInScreenPreview() {
    LinguaCelerisTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            EmailSignInScreenContent(EmailSignInUiState()) {}
        }
    }
}
