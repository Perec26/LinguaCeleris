package com.linguaceleris.auth.impl.ui.emailverification

import androidx.annotation.StringRes
import com.linguaceleris.auth.impl.R

internal data class EmailVerificationUiState(
    val isLoading: Boolean = false,
    val navigationBackIsAvailable: Boolean = false,
    val verificationState: EmailVerificationState = EmailVerificationState.INITIAL,
    val sendAgainEnable: Boolean = true,
    val sendAgainTimer: Int = 60,
    val email: String = "",
    val showEmailVerificationDialog: Boolean = false,
) {

    fun onShowEmailVerificationDialog() = copy(showEmailVerificationDialog = true)

    fun onHideEmailVerificationDialog() = copy(showEmailVerificationDialog = false)

    fun onSendingVerificationStarted() = copy(isLoading = true)

    fun onSendingVerificationFinished() = copy(isLoading = false)

    fun setSendAgainTimer(sendAgainTimer: Int) = copy(sendAgainTimer = sendAgainTimer)

    fun onVerificationStateChanged(verificationState: EmailVerificationState) = copy(
        verificationState = verificationState,
    )
}

internal enum class EmailVerificationState(
    @param:StringRes val title: Int,
    @param:StringRes val message: Int,
) {
    INITIAL(
        title = R.string.auth_email_verification_initial_title,
        message = R.string.auth_email_verification_initial_message,
    ),
    SUCCESS(
        title = R.string.auth_email_verification_success_title,
        message = R.string.auth_email_verification_success_message,
    )
}
