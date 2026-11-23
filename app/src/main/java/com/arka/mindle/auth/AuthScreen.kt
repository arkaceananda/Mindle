package com.arka.mindle.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.arka.mindle.R
import com.arka.mindle.ui.components.ButtonTone
import com.arka.mindle.ui.components.MindleButton
import com.arka.mindle.ui.theme.LocalAppColors
import com.arka.mindle.ui.theme.Spacing

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    onAuthSuccess: () -> Unit,
) {
    val form = viewModel.state.form
    val isLoading = viewModel.isLoading
    val context = LocalContext.current
    val scheme = MaterialTheme.colorScheme
    val app = LocalAppColors.current

    LaunchedEffect(viewModel.state) {
        if (viewModel.state is AuthUiState.Success) onAuthSuccess()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.screenHorizontal, vertical = Spacing.hero),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(
                if (form.isRegister) R.string.auth_headline_register else R.string.auth_headline_login,
            ),
            style = MaterialTheme.typography.displayMedium,
            color = scheme.onBackground,
        )
        Spacer(Modifier.height(Spacing.sm))
        Text(
            text = stringResource(R.string.auth_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = scheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(Spacing.hero))

        MindleButton(
            text = stringResource(R.string.auth_continue_google),
            onClick = { viewModel.signInWithGoogle(context) },
            modifier = Modifier.fillMaxWidth(),
            tone = ButtonTone.Secondary,
            enabled = !isLoading,
            leading = {
                Icon(
                    painter = painterResource(R.drawable.ic_google_g),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                )
            },
        )

        Spacer(Modifier.height(Spacing.lg))
        Row(verticalAlignment = Alignment.CenterVertically) {
            HorizontalDivider(Modifier.weight(1f), color = app.line)
            Text(
                text = stringResource(R.string.auth_or),
                style = MaterialTheme.typography.bodySmall,
                color = app.textTertiary,
                modifier = Modifier.padding(horizontal = Spacing.md),
            )
            HorizontalDivider(Modifier.weight(1f), color = app.line)
        }
        Spacer(Modifier.height(Spacing.lg))

        if (form.isRegister) {
            AuthField(
                value = form.displayName,
                onValueChange = viewModel::onDisplayNameChange,
                label = stringResource(R.string.auth_field_name),
            )
            Spacer(Modifier.height(Spacing.md))
        }

        AuthField(
            value = form.email,
            onValueChange = viewModel::onEmailChange,
            label = stringResource(R.string.auth_field_email),
            keyboardType = KeyboardType.Email,
        )
        Spacer(Modifier.height(Spacing.md))

        AuthField(
            value = form.password,
            onValueChange = viewModel::onPasswordChange,
            label = stringResource(R.string.auth_field_password),
            keyboardType = KeyboardType.Password,
            visualTransformation = if (form.showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailing = {
                TextButton(onClick = viewModel::togglePassword) {
                    Text(
                        text = stringResource(
                            if (form.showPassword) R.string.auth_password_hide else R.string.auth_password_show,
                        ),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            },
        )

        viewModel.errorMessage?.let { message ->
            Spacer(Modifier.height(Spacing.md))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = scheme.error,
            )
        }

        viewModel.resetMessage?.let { message ->
            Spacer(Modifier.height(Spacing.md))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = app.info,
            )
        }

        Spacer(Modifier.height(Spacing.xl))
        MindleButton(
            text = stringResource(
                if (form.isRegister) R.string.auth_submit_register else R.string.auth_submit_login,
            ),
            onClick = viewModel::submit,
            modifier = Modifier.fillMaxWidth(),
            loading = isLoading,
            enabled = !isLoading,
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = viewModel::toggleMode, enabled = !isLoading) {
                Text(
                    text = stringResource(
                        if (form.isRegister) R.string.auth_toggle_to_login else R.string.auth_toggle_to_register,
                    ),
                )
            }
            Spacer(Modifier.weight(1f))
            TextButton(onClick = viewModel::sendPasswordReset, enabled = !isLoading) {
                Text(text = stringResource(R.string.auth_forgot_password))
            }
        }

        Spacer(Modifier.height(Spacing.hero))
        Text(
            text = stringResource(R.string.auth_terms),
            style = MaterialTheme.typography.bodySmall,
            color = app.textTertiary,
        )
    }
}

@Composable
private fun AuthField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailing: (@Composable () -> Unit)? = null,
) {
    val scheme = MaterialTheme.colorScheme
    val app = LocalAppColors.current

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = trailing,
        shape = MaterialTheme.shapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = scheme.onSurface,
            unfocusedTextColor = scheme.onSurface,
            focusedContainerColor = scheme.surface,
            unfocusedContainerColor = scheme.surface,
            cursorColor = scheme.primary,
            focusedBorderColor = scheme.outline,
            unfocusedBorderColor = app.line,
            errorBorderColor = scheme.error,
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}
