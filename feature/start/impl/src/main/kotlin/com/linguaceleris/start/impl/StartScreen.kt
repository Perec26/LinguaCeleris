package com.linguaceleris.start.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme
import com.linguaceleris.designsystem.widgets.ScreenPreviews

@Composable
internal fun StartScreen(viewModel: StartViewModel) {
    val state = viewModel.state.collectAsState().value
    StartScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun StartScreenContent(state: StartUiState, onEvent: (StartEvent) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = { onEvent(StartEvent.OnButtonClick) },
            ) {
                Text("Start")
            }

            Button(
                onClick = { onEvent(StartEvent.OnButton2Click) },
            ) {
                Text(state.number.toString())
            }
        }
    }
}

@ScreenPreviews
@Composable
private fun StartScreenPreview() {
    LinguaCelerisTheme {
        StartScreenContent(StartUiState()) {}
    }
}
