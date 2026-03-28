package com.linguaceleris.designsystem.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingScaffold(
    modifier: Modifier = Modifier,
    title: String? = null,
    isLoading: Boolean = false,
    loadingText: String? = null,
    onNavigationButtonClick: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    LoadingWrapper(
        modifier = modifier,
        isLoading = isLoading,
        text = loadingText,
    ) {
        Scaffold(
            topBar = { TopBar(title, onNavigationButtonClick) },
            containerColor = Color.Transparent,
        ) { paddingValues ->
            content(paddingValues)
        }
    }
}

@Composable
private fun TopBar(title: String?, onNavigationButtonClick: (() -> Unit)?) {
    title?.let {
        CustomTopAppBar(
            title = title,
            onNavigationClick = onNavigationButtonClick,
        )
    }
}
