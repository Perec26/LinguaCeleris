package com.linguaceleris.auth.impl.ui.registration

internal sealed class RegistrationEvent {
    data class OnNickNameChanged(val nickName: String) : RegistrationEvent()
    data class OnEmailChanged(val email: String) : RegistrationEvent()
    data class OnPasswordChanged(val password: String) : RegistrationEvent()
    data class OnConfirmPasswordChanged(val confirmPassword: String) : RegistrationEvent()
    data object OnPasswordVisibilityChanged : RegistrationEvent()
    data object OnConfirmPasswordVisibilityChanged : RegistrationEvent()
    data object OnBackClicked : RegistrationEvent()
    data object OnRegisterClicked : RegistrationEvent()
    data object OnOpenMailClicked : RegistrationEvent()
    data object OnSendAgainClicked : RegistrationEvent()
    data object OnContinueClicked : RegistrationEvent()
    data object OnHideEmailVerificationDialog : RegistrationEvent()
}
