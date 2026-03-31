package com.linguaceleris.home.api

import androidx.navigation3.runtime.NavKey
import com.linguaceleris.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
data object HomeNavKey : NavKey

fun Navigator.startWithHome() = startWith(HomeNavKey)
