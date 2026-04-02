package com.linguaceleris.settings.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.linguaceleris.settings.impl.ui.SettingsScreen
import com.linguaceleris.settins.api.SettingsNavKey

fun EntryProviderScope<NavKey>.settingsEntry() {
    entry<SettingsNavKey> { _ -> SettingsScreen() }
}
