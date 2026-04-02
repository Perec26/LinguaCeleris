package com.linguaceleris.settins.api

import androidx.navigation3.runtime.NavKey
import com.linguaceleris.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
data object SettingsNavKey : NavKey

fun Navigator.navigateToSettings() = navigateTo(SettingsNavKey)
