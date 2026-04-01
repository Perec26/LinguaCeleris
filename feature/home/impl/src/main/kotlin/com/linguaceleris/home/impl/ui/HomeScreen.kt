package com.linguaceleris.home.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
        topBar = { TopBar(menuExpanded = state.menuExpanded, onEvent = onEvent) },
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
private fun TopBar(menuExpanded: Boolean, onEvent: (HomeEvent) -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(R.string.home_app_name)) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        actions = {
            Box {
                IconButton(
                    onClick = { onEvent(HomeEvent.OnOpenMenuClick) },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.home_menu),
                        contentDescription = null,
                    )
                }
                Menu(expanded = menuExpanded, onEvent = onEvent)
            }
        },
    )
}

@Composable
private fun Menu(expanded: Boolean, onEvent: (HomeEvent) -> Unit) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onEvent(HomeEvent.OnCloseMenuClick) },
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(R.string.home_setting)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.home_settings),
                    contentDescription = null,
                )
            },
            onClick = { onEvent(HomeEvent.OnSettingsClick) },
        )

        DropdownMenuItem(
            text = { Text(stringResource(R.string.home_exit)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.home_logout),
                    contentDescription = null,
                )
            },
            onClick = { onEvent(HomeEvent.OnSignOutClick) },
        )
    }
}

@ScreenPreviews
@Composable
private fun HomeScreenPreview() {
    LCPreview {
        HomeScreenContent(HomeUiState(dayQuizzes = quizzesUIMock)) {}
    }
}
