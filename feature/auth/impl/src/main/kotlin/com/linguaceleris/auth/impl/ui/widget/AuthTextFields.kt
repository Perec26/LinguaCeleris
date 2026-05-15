@file:PendingUiTests

package com.linguaceleris.auth.impl.ui.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.linguaceleris.auth.impl.R
import com.linguaceleris.auth.impl.ui.registration.model.AuthValidationResult
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.testing.PendingUiTests

@Composable
internal fun NicknameTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    validationResult: AuthValidationResult = AuthValidationResult.Success,
    imeAction: ImeAction = ImeAction.Unspecified,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = value,
        label = { Text(stringResource(R.string.auth_nickname)) },
        placeholder = { Text(stringResource(R.string.auth_enter_nickname)) },
        supportingText = { SupportText(validationResult) },
        isError = validationResult.isError,
        singleLine = true,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = imeAction,
        ),
    )
}

@Composable
internal fun EmailTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    validationResult: AuthValidationResult = AuthValidationResult.Success,
    imeAction: ImeAction = ImeAction.Unspecified,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = value,
        label = { Text(stringResource(R.string.auth_email)) },
        placeholder = { Text(stringResource(R.string.auth_type_email)) },
        supportingText = { SupportText(validationResult) },
        isError = validationResult.isError,
        singleLine = true,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction,
        ),
    )
}

@Composable
internal fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    validationResult: AuthValidationResult = AuthValidationResult.Success,
    isPasswordVisible: Boolean = false,
    imeAction: ImeAction = ImeAction.Unspecified,
    labelText: String = stringResource(R.string.auth_password),
    placeholderText: String = stringResource(R.string.auth_enter_password),
    onValueChange: (String) -> Unit,
    onVisibilityClick: () -> Unit,
) {
    TextField(
        modifier = modifier,
        value = value,
        label = { Text(labelText) },
        placeholder = { Text(placeholderText) },
        supportingText = { SupportText(validationResult) },
        isError = validationResult.isError,
        singleLine = true,
        visualTransformation = getVisualTransformation(isPasswordVisible),
        trailingIcon = { VisibilityIcon(isPasswordVisible, onVisibilityClick) },
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction,
        ),
    )
}

@Composable
internal fun ConfirmPasswordTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    validationResult: AuthValidationResult = AuthValidationResult.Success,
    isPasswordVisible: Boolean = false,
    imeAction: ImeAction = ImeAction.Unspecified,
    onValueChange: (String) -> Unit,
    onVisibilityClick: () -> Unit,
) {
    PasswordTextField(
        modifier = modifier,
        value = value,
        validationResult = validationResult,
        isPasswordVisible = isPasswordVisible,
        imeAction = imeAction,
        labelText = stringResource(R.string.auth_confirm_password),
        placeholderText = stringResource(R.string.auth_enter_confirm_password),
        onValueChange = onValueChange,
        onVisibilityClick = onVisibilityClick,
    )
}

@Composable
private fun SupportText(validationResult: AuthValidationResult) {
    if (validationResult is AuthValidationResult.Error) {
        Text(text = stringResource(validationResult.messageResId))
    }
}

private fun getVisualTransformation(isVisible: Boolean): VisualTransformation = if (isVisible) {
    VisualTransformation.None
} else {
    PasswordVisualTransformation()
}

@Composable
private fun VisibilityIcon(isVisible: Boolean, onClick: () -> Unit) {
    val icon = if (isVisible) R.drawable.auth_visibility_off else R.drawable.auth_visibility

    IconButton(onClick = onClick) {
        Icon(painter = painterResource(icon), contentDescription = null)
    }
}

@Preview
@Composable
private fun NicknameTextFieldPreview() {
    LCPreview {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            NicknameTextField(modifier = Modifier.fillMaxWidth()) {}

            EmailTextField(modifier = Modifier.fillMaxWidth()) {}

            PasswordTextField(modifier = Modifier.fillMaxWidth(), onValueChange = {}) {}

            ConfirmPasswordTextField(modifier = Modifier.fillMaxWidth(), onValueChange = {}) {}
        }
    }
}
