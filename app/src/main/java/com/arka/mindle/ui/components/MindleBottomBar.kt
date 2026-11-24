package com.arka.mindle.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(scheme.surface),
    ) {
        HorizontalDivider(color = app.line, thickness = 1.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
        ) {
            items.forEachIndexed { index, item ->
                val selected = index == selectedIndex
                val color = if (selected) scheme.onSurface else app.textTertiary
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(58.dp)
                        .clickable(role = Role.Tab) { onSelect(index) }
                        .padding(bottom = Spacing.sm),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = Spacing.sm)
                            .height(2.dp)
                            .width(24.dp)
                            .clip(MaterialTheme.shapes.extraSmall)
                            .background(if (selected) scheme.onSurface else scheme.surface),
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
