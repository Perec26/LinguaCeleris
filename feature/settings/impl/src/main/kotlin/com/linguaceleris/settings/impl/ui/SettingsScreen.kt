@file:PendingUiTests

package com.linguaceleris.settings.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.LoadingScaffold
import com.linguaceleris.designsystem.widgets.ScreenPreviews
import com.linguaceleris.settings.impl.R
import com.linguaceleris.settings.impl.ui.widget.SettingInfoElement
import com.linguaceleris.settings.impl.ui.widget.SettingToggleElement
import com.linguaceleris.testing.PendingUiTests

@Composable
internal fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
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
                    SettingToggleElement(
                        title = stringResource(R.string.settings_use_system_theme),
                        value = state.useSystemTheme,
                        onClick = { onEvent(SettingsEvent.OnUseSystemThemeClick) },
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    SettingToggleElement(
                        title = stringResource(R.string.settings_use_dark_theme),
                        value = state.useDarkTheme,
                        enabled = !state.useSystemTheme,
                        onClick = { onEvent(SettingsEvent.OnUseDarkThemeClick) },
                    )

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

                    SettingInfoElement(
                        title = "Версия",
                        value = state.version,
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
        SettingsScreenContent(SettingsUiState(isLoading = false, version = "0.0.25")) {}
    }
}

@ScreenPreviews
@Composable
private fun SettingsScreenLoadingPreview() {
    LCPreview {
        SettingsScreenContent(SettingsUiState()) {}
    }
}
