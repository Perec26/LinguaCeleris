@file:PendingUiTests

package com.linguaceleris.quiz.impl.ui.quiz

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.designsystem.R as UiR
import com.linguaceleris.designsystem.widgets.ButtonDescription
import com.linguaceleris.designsystem.widgets.CommonErrorWidget
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.LoadingScaffold
import com.linguaceleris.designsystem.widgets.ScreenPreviews
import com.linguaceleris.designsystem.widgets.ThreeButtonsDialog
import com.linguaceleris.quiz.impl.R
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI
import com.linguaceleris.quiz.impl.ui.quiz.widgets.LivesIndicator
import com.linguaceleris.quiz.impl.ui.quiz.widgets.MatchWidget
import com.linguaceleris.quiz.impl.ui.quiz.widgets.SelectCorrectWidget
import com.linguaceleris.testing.PendingUiTests
import com.linguaceleris.ui.ScreenState

private const val ANIMATION_DURATION = 300

@Composable
internal fun QuizScreen(viewModel: QuizViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    QuizScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun QuizScreenContent(state: QuizUiState, onEvent: (QuizEvent) -> Unit) {
    BackHandler { onEvent(QuizEvent.OnBackClick) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when (state.screenState) {
            ScreenState.LOADING -> CircularProgressIndicator()
            ScreenState.CONTENT -> Quiz(state, onEvent)
            ScreenState.ERROR -> CommonErrorWidget { onEvent(QuizEvent.OnReloadClick) }
        }

        if (state.showExitDialog) {
            ThreeButtonsDialog(
                title = stringResource(R.string.quiz_exit_dialog_title),
                description = stringResource(R.string.quiz_exit_dialog_description),
                okButtonDescription = ButtonDescription(
                    text = stringResource(R.string.quiz_exit),
                    onClick = { onEvent(QuizEvent.OnExitConfirmClick) },
                ),
                cancelButtonDescription = ButtonDescription(
                    text = stringResource(R.string.quiz_cancel),
                    onClick = { onEvent(QuizEvent.OnExitCancelClick) },
                ),
                onDismissRequest = { onEvent(QuizEvent.OnExitCancelClick) },
            )
        }
    }
}

@Composable
private fun Quiz(state: QuizUiState, onEvent: (QuizEvent) -> Unit) {
    LoadingScaffold(
        topBar = {
            TopPanel(
                progress = state.progressFloat,
                lives = state.lives,
                onBackClick = { onEvent(QuizEvent.OnBackClick) },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimatedContent(
                modifier = Modifier.weight(1f),
                targetState = state.currentTask,
                contentKey = { it?.id },
                transitionSpec = {
                    slideInHorizontally(tween(ANIMATION_DURATION)) { it } +
                        fadeIn(tween(ANIMATION_DURATION)) togetherWith
                        slideOutHorizontally(tween(ANIMATION_DURATION)) { -it } +
                        fadeOut(tween(ANIMATION_DURATION))
                },
            ) { task ->

                when (task) {
                    is TaskUI.SelectCorrectAnswer -> {
                        SelectCorrectWidget(
                            modifier = Modifier.weight(0.5f),
                            task = task,
                            onAudioClick = { onEvent(QuizEvent.OnAudioClick(it)) },
                            onCheckButtonClick = { onEvent(QuizEvent.OnCheckButtonClick) },
                            onContinueButtonClick = { onEvent(QuizEvent.OnContinueButtonClick) },
                            onVariantSelected = { variant ->
                                onEvent(QuizEvent.SelectAnswer(variant))
                            },
                        )
                    }

                    is TaskUI.Matching -> {
                        MatchWidget(
                            task = task,
                            onContinueButtonClick = { onEvent(QuizEvent.OnContinueButtonClick) },
                            onAudioClick = { onEvent(QuizEvent.OnAudioClick(it)) },
                            onVariantSelected = { onEvent(QuizEvent.SelectAnswer(it)) },
                        )
                    }

                    null -> {}
                }
            }
        }
    }
}

@Composable
private fun TopPanel(progress: Float, lives: Int, onBackClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                val animatedProgress by animateFloatAsState(
                    targetValue = progress,
                    animationSpec = tween(ANIMATION_DURATION),
                )
                LinearProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .weight(1f),
                    progress = { animatedProgress },
                )

                LivesIndicator(remainingLives = lives)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(UiR.drawable.arrow_back),
                    contentDescription = "",
                )
            }
        },
    )
}

@ScreenPreviews
@Composable
private fun QuizScreenContentPreviewAudioText() {
    LCPreview {
        QuizScreenContent(
            state = quizStateMock,
        ) {}
    }
}

@ScreenPreviews
@Composable
private fun QuizScreenContentPreviewAudio() {
    LCPreview {
        QuizScreenContent(
            state = quizStateMock.copy(currentTask = listenMock),
        ) {}
    }
}

@ScreenPreviews
@Composable
private fun QuizScreenContentPreviewText() {
    LCPreview {
        QuizScreenContent(
            state = quizStateMock.copy(currentTask = selectAudioMock),
        ) {}
    }
}

@ScreenPreviews
@Composable
private fun QuizScreenContentPreviewImage() {
    LCPreview {
        QuizScreenContent(
            state = quizStateMock.copy(currentTask = imageSelectWordTranslation),
        ) {}
    }
}

@ScreenPreviews
@Composable
private fun QuizScreenLoadingPreview() {
    LCPreview {
        QuizScreenContent(
            state = quizStateMock.copy(screenState = ScreenState.LOADING),
        ) {}
    }
}

@ScreenPreviews
@Composable
private fun QuizScreenErrorPreview() {
    LCPreview {
        QuizScreenContent(
            state = quizStateMock.copy(screenState = ScreenState.ERROR),
        ) {}
    }
}
