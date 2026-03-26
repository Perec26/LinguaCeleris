package com.linguaceleris.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.linguaceleris.auth.impl.navigation.authSelectionEntry
import com.linguaceleris.login.impl.navigation.loginEntry
import com.linguaceleris.quiz.impl.navigation.quizEntry
import com.linguaceleris.quizselection.impl.navigation.quizSelectionEntry
import com.linguaceleris.quizsummary.impl.navigation.quizSummaryEntry
import com.linguaceleris.start.impl.navigation.startEntry
import com.linguaceleris.ui.LocalSnackbarHostState

@Composable
internal fun LCApp(navigator: Navigator) {
    val snackbarHostState = remember { SnackbarHostState() }

    val entryProvider = entryProvider {
        authSelectionEntry()
        startEntry()
        quizEntry()
        quizSelectionEntry()
        quizSummaryEntry()
        loginEntry()
    }

    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = Color.Transparent,
        ) { paddingValues ->
            paddingValues
            NavDisplay(
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator(),
                ),
                backStack = navigator.backStack,
                entryProvider = entryProvider,
                onBack = navigator::back,
            )
        }
    }
}
