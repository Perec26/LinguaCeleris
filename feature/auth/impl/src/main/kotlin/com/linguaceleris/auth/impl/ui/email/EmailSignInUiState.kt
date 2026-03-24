package com.linguaceleris.auth.impl.ui.email

internal data class EmailSignInUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
) {
    fun onEmailChange(email: String) = copy(email = email)

    fun onPasswordChange(password: String) = copy(password = password)

    fun onPasswordVisibilityChange() = copy(isPasswordVisible = !isPasswordVisible)
}
