package com.linguaceleris.auth.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object SignInNavKey : NavKey

@Serializable
data class EmailVerificationNavKey(
    val fromStart: Boolean,
) : NavKey
