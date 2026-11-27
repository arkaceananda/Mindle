package com.arka.mindle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arka.mindle.R
import com.arka.mindle.model.Course
import com.arka.mindle.model.CourseCategory
import com.arka.mindle.model.CourseFormat
import com.arka.mindle.ui.theme.LocalAppColors
import com.arka.mindle.ui.theme.NumM
import com.arka.mindle.ui.theme.Spacing
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CourseCard(
    course: Course,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    val app = LocalAppColors.current
    val shape = MaterialTheme.shapes.medium

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .border(1.dp, app.line, shape)
            .background(scheme.surface)
            .clickable(onClick = onClick),
    ) {
        Box {
            CourseCover(
                category = course.category,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
            )
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(Spacing.md),
                horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
            ) {
                if (course.isNew) StateChip(label = stringResource(R.string.course_state_new))
                if (course.isEdited) StateChip(label = stringResource(R.string.course_state_edited))
            }
            if (course.progressPercent != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(app.line),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(course.progressPercent / 100f)
                            .height(2.dp)
                            .background(scheme.primary),
                    )
                }
            }
        }

        Column(modifier = Modifier.padding(Spacing.lg)) {
            Text(
                text = course.title,
                style = MaterialTheme.typography.titleLarge,
                color = scheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(Spacing.xs))
            Text(
                text = "${course.creatorName} · ${formatRating(course.creatorReputation)} ★",
                style = MaterialTheme.typography.bodySmall,
                color = scheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(Spacing.sm))
            Row(verticalAlignment = Alignment.CenterVertically) {
                MetaIcon(R.drawable.ic_duration)
                Text(
                    text = stringResource(R.string.course_meta_duration, course.durationMinutes),
                    style = NumM,
                    color = scheme.onSurfaceVariant,
                )
                MetaDot()
                Text(
                    text = stringResource(
                        if (course.format == CourseFormat.ARTICLE) R.string.course_format_article
                        else R.string.course_format_video,
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = scheme.onSurfaceVariant,
                )
                MetaDot()
                MetaIcon(R.drawable.ic_learners)
                Text(
                    text = formatLearners(course.learnerCount),
                    style = NumM,
                    color = scheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
fun CompactCourseCard(
    course: Course,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    val app = LocalAppColors.current
    val shape = MaterialTheme.shapes.medium

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .border(1.dp, app.line, shape)
            .background(scheme.surface)
            .clickable(onClick = onClick)
            .padding(Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CourseCover(
            category = course.category,
            modifier = Modifier
                .size(width = 88.dp, height = 56.dp)
                .clip(MaterialTheme.shapes.small),
        )
        Spacer(Modifier.width(Spacing.lg))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = course.title,
                style = MaterialTheme.typography.titleMedium,
                color = scheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(Spacing.xxs))
            Text(
                text = course.creatorName,
                style = MaterialTheme.typography.bodySmall,
                color = scheme.onSurfaceVariant,
                maxLines = 1,
            )
            course.progressPercent?.let { pct ->
                Spacer(Modifier.height(Spacing.xs))
                Text(
                    text = stringResource(R.string.course_resume, pct),
                    style = NumM,
                    color = scheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun CourseCover(category: CourseCategory, modifier: Modifier = Modifier) {
    val app = LocalAppColors.current
    val scheme = MaterialTheme.colorScheme
    Box(
        modifier = modifier.background(app.surfaceSunken),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(category.iconRes()),
            contentDescription = null,
            tint = scheme.onSurfaceVariant,
            modifier = Modifier.size(32.dp),
        )
    }
}

@Composable
private fun StateChip(label: String) {
    val app = LocalAppColors.current
    val scheme = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .border(1.dp, app.lineStrong, MaterialTheme.shapes.extraSmall)
            .background(scheme.surface.copy(alpha = 0.92f), MaterialTheme.shapes.extraSmall)
            .padding(horizontal = Spacing.sm, vertical = Spacing.xxs),
    ) {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = scheme.onSurface)
    }
}

@Composable
fun MetaIcon(res: Int) {
    Icon(
        painter = painterResource(res),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.size(14.dp),
    )
    Spacer(Modifier.width(Spacing.xs))
}

@Composable
fun MetaDot() {
    Text(
        text = " · ",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

fun CourseCategory.iconRes(): Int = when (this) {
    CourseCategory.TECHNOLOGY -> R.drawable.ic_cat_tech
    CourseCategory.LANGUAGE -> R.drawable.ic_cat_language
    CourseCategory.BUSINESS -> R.drawable.ic_cat_business
    CourseCategory.DESIGN -> R.drawable.ic_cat_design
    CourseCategory.SCIENCE -> R.drawable.ic_cat_science
    CourseCategory.GROWTH -> R.drawable.ic_cat_growth
}

fun CourseCategory.labelRes(): Int = when (this) {
    CourseCategory.TECHNOLOGY -> R.string.category_technology
    CourseCategory.LANGUAGE -> R.string.category_language
    CourseCategory.BUSINESS -> R.string.category_business
    CourseCategory.DESIGN -> R.string.category_design
    CourseCategory.SCIENCE -> R.string.category_science
    CourseCategory.GROWTH -> R.string.category_growth
}

private val IdLocale: Locale = Locale.forLanguageTag("id-ID")

private fun formatRating(value: Double): String = String.format(IdLocale, "%.1f", value)

private fun formatLearners(count: Int): String {
    val formatted = if (count >= 1000) {
        String.format(IdLocale, "%.1fk", count / 1000f)
    } else {
        NumberFormat.getIntegerInstance(IdLocale).format(count)
    }
    return formatted
}
