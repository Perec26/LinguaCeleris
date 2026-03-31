package com.linguaceleris.home.impl.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme
import com.linguaceleris.designsystem.widgets.ScreenPreviews

@Composable
internal fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    HomeScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun HomeScreenContent(state: HomeUiState, onEvent: (HomeEvent) -> Unit) {
}

@ScreenPreviews
@Composable
private fun HomeScreenPreview() {
    LinguaCelerisTheme {
        HomeScreenContent(HomeUiState()) {}
    }
}
