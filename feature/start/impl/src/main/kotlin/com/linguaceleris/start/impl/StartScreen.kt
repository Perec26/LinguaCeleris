package com.linguaceleris.start.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.ScreenPreviews

@Composable
internal fun StartScreen(viewModel: StartViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    StartScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun StartScreenContent(state: StartUiState, onEvent: (StartEvent) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@ScreenPreviews
@Composable
private fun StartScreenPreview() {
    LCPreview {
        StartScreenContent(StartUiState()) {}
    }
}
