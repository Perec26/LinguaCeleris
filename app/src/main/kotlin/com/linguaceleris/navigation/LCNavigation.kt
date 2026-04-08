package com.linguaceleris.navigation

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import com.linguaceleris.auth.impl.navigation.authEntry
import com.linguaceleris.home.impl.navigation.homeEntry
import com.linguaceleris.quiz.impl.navigation.quizEntry
import com.linguaceleris.settings.impl.navigation.settingsEntry
import com.linguaceleris.start.impl.navigation.startEntry
import com.linguaceleris.ui.LocalSnackbarHostState

@Composable
internal fun LCApp(navigator: Navigator) {
    val snackbarHostState = remember { SnackbarHostState() }

    val entryProvider = entryProvider {
        authEntry()
        homeEntry()
        settingsEntry()
        startEntry()
        quizEntry()
    }

    val duration = 300

    val slideInFromLeft = slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween(duration, easing = EaseInOutCubic),
    )
    val slideOutToRight = slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween(duration, easing = EaseInOutCubic),
    )

    val slideInFromRight = slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(duration, easing = EaseInOutCubic),
    )
    val slideOutToLeft = slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween(duration, easing = EaseInOutCubic),
    )

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
                transitionSpec = {
                    slideInFromRight togetherWith (slideOutToLeft + fadeOut(tween(duration)))
                },

                popTransitionSpec = {
                    slideInFromLeft togetherWith (slideOutToRight + fadeOut(tween(duration)))
                },

                predictivePopTransitionSpec = {
                    slideInFromLeft togetherWith (slideOutToRight + fadeOut(tween(duration)))
                },
                entryProvider = entryProvider,
                onBack = navigator::back,
            )
        }
    }
}
