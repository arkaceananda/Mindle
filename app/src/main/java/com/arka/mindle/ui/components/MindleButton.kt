package com.arka.mindle.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arka.mindle.ui.theme.LocalAppColors
import com.arka.mindle.ui.theme.Spacing

// §6.3 button set. Primary fill is the near-black text color on purpose.
enum class ButtonTone { Primary, Secondary, Tonal, Text }

@Composable
fun MindleButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tone: ButtonTone = ButtonTone.Primary,
    enabled: Boolean = true,
    loading: Boolean = false,
    leading: (@Composable () -> Unit)? = null,
) {
    val scheme = MaterialTheme.colorScheme
    val app = LocalAppColors.current
    val colors = when (tone) {
        ButtonTone.Primary -> ButtonDefaults.buttonColors(
            containerColor = scheme.primary,
            contentColor = scheme.onPrimary,
        )
        ButtonTone.Secondary -> ButtonDefaults.buttonColors(
            containerColor = scheme.surface,
            contentColor = scheme.onSurface,
        )
        ButtonTone.Tonal -> ButtonDefaults.buttonColors(
            containerColor = scheme.surfaceVariant,
            contentColor = scheme.onSurface,
        )
        ButtonTone.Text -> ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = scheme.primary,
        )
    }

    Button(
        onClick = onClick,
        modifier = modifier.heightIn(min = Spacing.touchTarget),
        enabled = enabled && !loading,
        shape = MaterialTheme.shapes.medium,
        colors = colors,
        border = if (tone == ButtonTone.Secondary) BorderStroke(1.dp, app.lineStrong) else null,
        elevation = null,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = if (tone == ButtonTone.Primary) scheme.onPrimary else scheme.primary,
                )
                Spacer(Modifier.width(Spacing.sm))
            }
            leading?.let {
                it()
                Spacer(Modifier.width(Spacing.sm))
            }
            Text(text = text, style = MaterialTheme.typography.labelLarge)
        }
    }
}
