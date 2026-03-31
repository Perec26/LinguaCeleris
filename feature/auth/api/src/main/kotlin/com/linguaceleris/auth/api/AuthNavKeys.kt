package com.linguaceleris.auth.api

import androidx.navigation3.runtime.NavKey
import com.linguaceleris.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
data object SignInNavKey : NavKey

@Serializable
data class EmailVerificationNavKey(val fromStart: Boolean) : NavKey

fun Navigator.startWithSignIn() = startWith(SignInNavKey)
fun Navigator.startWithEmailVerification() = startWith(EmailVerificationNavKey(true))
fun Navigator.navigateToEmailVerification() = navigateTo(EmailVerificationNavKey(false))
