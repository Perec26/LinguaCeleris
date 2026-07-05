@file:PendingUiTests

package com.linguaceleris.auth.impl.ui.linkaccount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.linguaceleris.auth.impl.R
import com.linguaceleris.auth.impl.ui.widget.GoogleSignInButton
import com.linguaceleris.auth.impl.ui.widget.triggerGoogleSignIn
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.LoadingScaffold
import com.linguaceleris.designsystem.widgets.ScreenPreviews
import com.linguaceleris.testing.PendingUiTests
import com.linguaceleris.ui.LocalSnackbarHostState

@Composable
internal fun LinkAccountScreen(viewModel: LinkAccountViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    val context = LocalContext.current
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is LinkAccountEffect.SignInWithGoogle -> {
                    triggerGoogleSignIn(
                        context = context,
                        webClientId = effect.webClientId,
                        onTokenReceived = {
                            viewModel.onEvent(LinkAccountEvent.OnGoogleTokenReceived(it))
                        },
                        onError = {
                            viewModel.onEvent(LinkAccountEvent.OnGoogleGetCredentialException(it))
                        },
                    )
                }

                is LinkAccountEffect.ShowSnackBarError -> {
                    snackbarHostState.showSnackbar(
                        message = effect.error.message.asString(context),
                        withDismissAction = true,
                    )
                }
            }
        }
    }

    LinkAccountScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun LinkAccountScreenContent(
    state: LinkAccountUiState,
    onEvent: (LinkAccountEvent) -> Unit,
) {
    LoadingScaffold(
        modifier = Modifier.fillMaxSize(),
        isLoading = state.isLoading,
        title = stringResource(id = R.string.auth_link_account),
        onNavigationButtonClick = { onEvent(LinkAccountEvent.OnBackClicked) },
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Column(
                modifier = Modifier.weight(1f).padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Text(
                        text = stringResource(R.string.auth_link_account_title),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Text(
                        text = stringResource(R.string.auth_link_account_subtitle),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,

                    )
                }
            }

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                GoogleSignInButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onEvent(LinkAccountEvent.OnGoogleSignInClick) },
                )
            }
        }
    }
}

@ScreenPreviews
@Composable
private fun LinkAccountScreenPreview() {
    LCPreview {
        LinkAccountScreenContent(LinkAccountUiState()) {}
    }
}
