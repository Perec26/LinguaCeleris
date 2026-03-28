package com.linguaceleris.auth.impl.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.linguaceleris.auth.api.EmailVerificationNavKey
import com.linguaceleris.auth.api.SignInNavKey
import com.linguaceleris.auth.impl.ui.email.EmailSignInScreen
import com.linguaceleris.auth.impl.ui.emailverification.EmailVerificationScreen
import com.linguaceleris.auth.impl.ui.emailverification.EmailVerificationViewModel
import com.linguaceleris.auth.impl.ui.forgotpassword.ForgotPasswordScreen
import com.linguaceleris.auth.impl.ui.forgotpassword.ForgotPasswordViewModel
import com.linguaceleris.auth.impl.ui.registration.RegistrationScreen
import com.linguaceleris.auth.impl.ui.signin.SignInScreen
import kotlinx.serialization.Serializable

@Serializable
internal data object EmailSignInNavKey : NavKey

@Serializable
internal data object RegistrationNavKey : NavKey

@Serializable
internal data class ForgotPasswordNavKey(
    val email: String,
) : NavKey

fun EntryProviderScope<NavKey>.authEntry() {
    entry<SignInNavKey> { _ -> SignInScreen() }
    entry<EmailSignInNavKey> { _ -> EmailSignInScreen() }
    entry<RegistrationNavKey> { _ -> RegistrationScreen() }
    entry<ForgotPasswordNavKey> { key ->
        val viewModel = hiltViewModel<ForgotPasswordViewModel, ForgotPasswordViewModel.Factory>(
            key = key.email,
        ) {
            it.create(key.email)
        }
        ForgotPasswordScreen(viewModel)
    }
    entry<EmailVerificationNavKey> { key ->
        val viewModel =
            hiltViewModel<EmailVerificationViewModel, EmailVerificationViewModel.Factory>(
                key = key.fromStart.toString(),
            ) {
                it.create(key.fromStart)
            }
        EmailVerificationScreen(viewModel)
    }
}
