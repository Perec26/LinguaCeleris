package com.linguaceleris.auth.impl.ui.linkaccount

internal data class LinkAccountUiState(val isLoading: Boolean = false) {
    fun showLoading() = copy(isLoading = true)
    fun hideLoading() = copy(isLoading = false)
}
