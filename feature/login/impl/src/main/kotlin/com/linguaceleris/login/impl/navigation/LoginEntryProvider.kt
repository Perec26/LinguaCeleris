package com.linguaceleris.login.impl.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.linguaceleris.login.api.LoginNavKey
import com.linguaceleris.login.impl.LoginScreen
import com.linguaceleris.login.impl.LoginViewModel

fun EntryProviderScope<NavKey>.loginEntry() {
    entry<LoginNavKey> { key ->
        val viewModel = hiltViewModel<LoginViewModel, LoginViewModel.Factory>(
            key = key.number.toString(),
        ) {
            it.create(key.number)
        }
        LoginScreen(viewModel)
    }
}
