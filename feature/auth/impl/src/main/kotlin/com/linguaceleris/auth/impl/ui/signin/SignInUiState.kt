package com.linguaceleris.auth.impl.ui.signin

internal data class SignInUiState(
    val showAnonymousSignInDialog: Boolean = false,
    val isLoading: Boolean = false,
) {
    fun showAnonymousSignInDialog() = copy(showAnonymousSignInDialog = true)
    fun hideAnonymousSignInDialog() = copy(showAnonymousSignInDialog = false)
    fun showLoading() = copy(isLoading = true)
    fun hideLoading() = copy(isLoading = false)
}
