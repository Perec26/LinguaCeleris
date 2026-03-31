package com.linguaceleris.home.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.linguaceleris.home.api.HomeNavKey
import com.linguaceleris.home.impl.ui.HomeScreen

fun EntryProviderScope<NavKey>.homeEntry() {
    entry<HomeNavKey> { _ -> HomeScreen() }
}
