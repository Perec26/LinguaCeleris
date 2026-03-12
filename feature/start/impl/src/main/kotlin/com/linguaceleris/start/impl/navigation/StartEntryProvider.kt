package com.linguaceleris.start.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.linguaceleris.start.api.StartNavKey
import com.linguaceleris.start.impl.StartScreen

fun EntryProviderScope<NavKey>.startEntry() {
    entry<StartNavKey> { _ -> StartScreen() }
}
