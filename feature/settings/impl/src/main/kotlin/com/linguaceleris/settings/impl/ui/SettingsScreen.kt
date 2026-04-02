package com.linguaceleris.settings.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.LoadingScaffold
import com.linguaceleris.designsystem.widgets.ScreenPreviews
import com.linguaceleris.settings.impl.R
import com.linguaceleris.settings.impl.ui.widget.SettingElement

@Composable
internal fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    SettingsScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun SettingsScreenContent(state: SettingsUiState, onEvent: (SettingsEvent) -> Unit) {
    LoadingScaffold(
        title = stringResource(R.string.settings_title),
        onNavigationButtonClick = { onEvent(SettingsEvent.OnBackPressed) },
    ) { paddingValues ->

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = paddingValues),
                ) {
                    SettingElement(
                        title = stringResource(R.string.settings_use_system_theme),
                        value = state.useSystemTheme,
                        onClick = { onEvent(SettingsEvent.OnUseSystemThemeClick) },
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    SettingElement(
                        title = stringResource(R.string.settings_use_dark_theme),
                        value = state.useDarkTheme,
                        enabled = !state.useSystemTheme,
                        onClick = { onEvent(SettingsEvent.OnUseDarkThemeClick) },
                    )
                }
            }
        }
    }
}

@ScreenPreviews
@Composable
private fun SettingsScreenPreview() {
    LCPreview {
        SettingsScreenContent(SettingsUiState()) {}
    }
}
