package com.linguaceleris.home.impl.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.designsystem.widgets.CommonErrorWidget
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.LoadingScaffold
import com.linguaceleris.designsystem.widgets.ScreenPreviews
import com.linguaceleris.home.impl.R
import com.linguaceleris.home.impl.ui.model.StreakUI
import com.linguaceleris.home.impl.ui.widget.QuizSelectionWidget
import com.linguaceleris.home.impl.ui.widget.SocialButtonsWidget
import com.linguaceleris.home.impl.ui.widget.StreakWidget
import com.linguaceleris.ui.ScreenState
import com.linguaceleris.ui.utils.UiText

@Composable
internal fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                is HomeEffect.OpenTelegram -> context.openLink(it.link)
                is HomeEffect.OpenYoutube -> context.openLink(it.link)
            }
        }
    }
    HomeScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun HomeScreenContent(state: HomeUiState, onEvent: (HomeEvent) -> Unit) {
    LoadingScaffold(
        topBar = { TopBar(menuExpanded = state.menuExpanded, onEvent = onEvent) },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center,
        ) {
            when (state.screenState) {
                ScreenState.LOADING -> CircularProgressIndicator()

                ScreenState.ERROR -> CommonErrorWidget { onEvent(HomeEvent.OnRefreshClick) }

                ScreenState.CONTENT ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        StreakWidget(streak = state.streak)

                        Timer(state.needToAlarm, state.nextQuizzesTimer)

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(
                                space = 16.dp,
                                alignment = Alignment.CenterVertically,
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = stringResource(R.string.home_choose_level),
                                style = MaterialTheme.typography.headlineMedium,
                            )
                            QuizSelectionWidget(state.streak.completion, onEvent)
                        }

                        SocialButtonsWidget(onEvent)
                    }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun Timer(isAlarm: Boolean, timerText: UiText) {
    if (isAlarm) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(
                    MaterialTheme.colorScheme.errorContainer,
                    shape = MaterialTheme.shapes.medium,
                ),

            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.home_timer_alarm, timerText.asString()),
                color = MaterialTheme.colorScheme.onErrorContainer,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    } else {
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(R.string.home_timer, timerText.asString()),
            color = MaterialTheme.colorScheme.onSurface,
        )
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

fun Context.openLink(link: UiText) {
    val intent = Intent(Intent.ACTION_VIEW, link.asString(this).toUri())
    startActivity(intent)
}

@ScreenPreviews
@Composable
private fun HomeScreenPreview() {
    LCPreview {
        HomeScreenContent(
            HomeUiState(
                nextQuizzesTimer = UiText.DynamicString("12:34:56"),
                screenState = ScreenState.CONTENT,
            ),
        ) {}
    }
}

@ScreenPreviews
@Composable
private fun HomeScreenAlarmPreview() {
    LCPreview {
        HomeScreenContent(
            HomeUiState(
                streak = StreakUI.TodayNotCompleted(123),
                nextQuizzesTimer = UiText.DynamicString("34:56"),
                lastHour = true,
                screenState = ScreenState.CONTENT,
            ),
        ) {}
    }
}

@ScreenPreviews
@Composable
private fun HomeScreenLoadingPreview() {
    LCPreview {
        HomeScreenContent(HomeUiState()) {}
    }
}

@ScreenPreviews
@Composable
private fun HomeScreenErrorPreview() {
    LCPreview {
        HomeScreenContent(HomeUiState(screenState = ScreenState.ERROR)) {}
    }
}
