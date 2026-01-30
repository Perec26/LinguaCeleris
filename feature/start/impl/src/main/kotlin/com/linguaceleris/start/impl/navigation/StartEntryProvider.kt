package com.linguaceleris.start.impl.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.linguaceleris.start.api.StartNavKey
import com.linguaceleris.start.impl.StartScreen
import com.linguaceleris.start.impl.StartViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun EntryProviderScope<NavKey>.startEntry() {
    entry<StartNavKey> { _ ->
        val viewModel = hiltViewModel<StartViewModel>()
        StartScreen(viewModel)
    }
}
