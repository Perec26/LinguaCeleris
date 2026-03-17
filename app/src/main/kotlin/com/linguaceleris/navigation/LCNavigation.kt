package com.linguaceleris.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.linguaceleris.login.impl.navigation.loginEntry
import com.linguaceleris.quiz.impl.navigation.quizEntry
import com.linguaceleris.quizselection.impl.navigation.quizSelectionEntry
import com.linguaceleris.start.impl.navigation.startEntry

@Composable
internal fun LCApp(navigator: Navigator) {
    val entryProvider = entryProvider {
        startEntry()
        quizEntry()
        quizSelectionEntry()
        loginEntry()
    }

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
