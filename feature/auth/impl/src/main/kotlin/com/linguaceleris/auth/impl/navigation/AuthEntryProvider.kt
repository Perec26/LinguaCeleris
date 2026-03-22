package com.linguaceleris.auth.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.linguaceleris.auth.api.SignInNavKey
import com.linguaceleris.auth.impl.ui.signin.SignInScreen

fun EntryProviderScope<NavKey>.authSelectionEntry() {
    entry<SignInNavKey> { _ -> SignInScreen() }
}
