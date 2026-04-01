package com.linguaceleris.home.impl.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.LoadingScaffold
import com.linguaceleris.designsystem.widgets.ScreenPreviews
import com.linguaceleris.home.impl.R
import com.linguaceleris.home.impl.ui.widget.QuizSelectionWidget

@Composable
internal fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    HomeScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun HomeScreenContent(state: HomeUiState, onEvent: (HomeEvent) -> Unit) {
    LoadingScaffold(
        topBar = { TopBar(onEvent) },
    ) {
        Column {
            QuizSelectionWidget(
                quizzesUI = state.dayQuizzes,
                isLoading = false,
                onEvent = onEvent,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(onEvent: (HomeEvent) -> Unit) {
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(horizontal = 16.dp),
        title = { Text("Lingua Celeris") },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        actions = {
            Icon(
                modifier = Modifier.clickable { onEvent(HomeEvent.OnOpenMenuClick) },
                painter = painterResource(id = R.drawable.home_menu),
                contentDescription = null,
            )
        },
    )
}

@ScreenPreviews
@Composable
private fun HomeScreenPreview() {
    LCPreview {
        HomeScreenContent(HomeUiState(dayQuizzes = quizzesUIMock)) {}
    }
}
