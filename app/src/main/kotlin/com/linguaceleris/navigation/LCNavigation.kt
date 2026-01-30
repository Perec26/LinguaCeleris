package com.linguaceleris.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.linguaceleris.login.impl.navigation.loginEntry
import com.linguaceleris.start.impl.navigation.startEntry

@Composable
internal fun LCApp(navigator: Navigator) {
    val entryProvider = entryProvider {
        startEntry()
        loginEntry()
    }

    NavDisplay(
        backStack = navigator.backStack,
        entryProvider = entryProvider,
    )
}
