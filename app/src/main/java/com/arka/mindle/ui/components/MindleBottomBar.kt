package com.arka.mindle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.arka.mindle.ui.theme.LocalAppColors
import com.arka.mindle.ui.theme.Spacing

data class BottomNavItem(
    val label: String,
    val icon: Painter,
    val selectedIcon: Painter,
)

@Composable
fun MindleBottomBar(
    items: List<BottomNavItem>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    val app = LocalAppColors.current
    val shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.xl, vertical = Spacing.md),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 4.dp,
                    shape = shape,
                    clip = false,
                    ambientColor = scheme.onSurface.copy(alpha = 0.08f),
                    spotColor = scheme.onSurface.copy(alpha = 0.08f),
                )
                .clip(shape)
                .border(1.dp, app.line, shape)
                .background(scheme.surface, shape)
                .height(64.dp)
                .padding(horizontal = Spacing.sm),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            items.forEachIndexed { index, item ->
                val selected = index == selectedIndex
                val color = if (selected) scheme.onSurface else app.textTertiary
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(64.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable(role = Role.Tab) { onSelect(index) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = Spacing.sm)
                            .height(2.dp)
                            .width(20.dp)
                            .clip(MaterialTheme.shapes.extraSmall)
                            .background(if (selected) scheme.onSurface else androidx.compose.ui.graphics.Color.Transparent),
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(
                        painter = if (selected) item.selectedIcon else item.icon,
                        contentDescription = item.label,
                        tint = color,
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = color,
                        maxLines = 1,
                    )
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}
