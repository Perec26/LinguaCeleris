package com.linguaceleris.login.impl.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.linguaceleris.login.api.LoginNavKey
import com.linguaceleris.login.impl.LoginScreen
import com.linguaceleris.login.impl.LoginViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
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
