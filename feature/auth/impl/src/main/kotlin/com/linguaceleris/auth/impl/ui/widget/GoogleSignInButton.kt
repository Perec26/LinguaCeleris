@file:PendingUiTests

package com.linguaceleris.auth.impl.ui.widget

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.linguaceleris.auth.impl.R
import com.linguaceleris.designsystem.widgets.HapticElement
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.testing.PendingUiTests
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun GoogleSignInButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val size = ButtonDefaults.MediumContainerHeight

    val colors = ButtonDefaults.buttonColors().copy(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    )

    HapticElement { haptic ->
        Button(
            modifier = modifier,
            colors = colors,
            contentPadding = ButtonDefaults.contentPaddingFor(size, hasStartIcon = true),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                onClick.invoke()
            },
        ) {
            Image(
                painter = painterResource(R.drawable.auth_google_logo),
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.iconSizeFor(size)),
            )

            Spacer(Modifier.size(ButtonDefaults.iconSpacingFor(size)))

            Text(
                text = stringResource(R.string.auth_google_sign_in),
                style = ButtonDefaults.textStyleFor(size),
            )
        }
    }
}

internal fun CoroutineScope.triggerGoogleSignIn(
    context: Context,
    webClientId: String,
    onTokenReceived: (String) -> Unit,
    onError: (Exception) -> Unit,
) {
    launch {
        try {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setServerClientId(webClientId)
                .setFilterByAuthorizedAccounts(false)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val response = CredentialManager.create(context).getCredential(
                context = context,
                request = request,
            )

            val googleIdTokenCredential = response.credential
            val idToken = GoogleIdTokenCredential.createFrom(
                googleIdTokenCredential.data,
            ).idToken
            onTokenReceived(idToken)
        } catch (exception: Exception) {
            onError(exception)
        }
    }
}

@PreviewLightDark
@Composable
private fun GoogleSignInButtonPreview() {
    LCPreview {
        GoogleSignInButton(
            modifier = Modifier.padding(16.dp),
        ) {}
    }
}
