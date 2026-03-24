package com.linguaceleris.auth.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.linguaceleris.auth.api.SignInNavKey
import com.linguaceleris.auth.impl.ui.email.EmailSignInScreen
import com.linguaceleris.auth.impl.ui.registration.RegistrationScreen
import com.linguaceleris.auth.impl.ui.signin.SignInScreen

internal data object EmailSignInNavKey : NavKey
internal data object RegistrationNavKey : NavKey

fun EntryProviderScope<NavKey>.authSelectionEntry() {
    entry<SignInNavKey> { _ -> SignInScreen() }
    entry<EmailSignInNavKey> { _ -> EmailSignInScreen() }
    entry<RegistrationNavKey> { _ -> RegistrationScreen() }
}
