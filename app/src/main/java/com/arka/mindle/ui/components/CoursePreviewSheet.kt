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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arka.mindle.R
import com.arka.mindle.data.MockCourses
import com.arka.mindle.model.Course
import com.arka.mindle.ui.theme.LocalAppColors
import com.arka.mindle.ui.theme.NumM
import com.arka.mindle.ui.theme.Spacing

@Composable
fun CoursePreviewSheet(
    course: Course,
    onStartLearning: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    val app = LocalAppColors.current
    val preview = MockCourses.previewData[course.id]
    val progress = course.progressPercent

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.screenHorizontal, vertical = Spacing.lg),
    ) {
        // Cover
        CourseCoverLarge(category = course.category, modifier = Modifier.fillMaxWidth().aspectRatio(16f / 9f))
        Spacer(Modifier.height(Spacing.lg))

        // Title
        Text(
            text = course.title,
            style = MaterialTheme.typography.displayMedium,
            color = scheme.onSurface,
        )
        Spacer(Modifier.height(Spacing.xs))

        // Creator row
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(app.surfaceSunken)
                    .border(1.dp, app.line, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = course.creatorName.take(1).uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = scheme.onSurfaceVariant,
                )
            }
            Spacer(Modifier.width(Spacing.sm))
            Text(
                text = course.creatorName,
                style = MaterialTheme.typography.bodyMedium,
                color = scheme.onSurfaceVariant,
            )
            Spacer(Modifier.width(Spacing.sm))
            Text(
                text = "· ${formatRating(course.creatorReputation)} ★",
                style = NumM,
                color = scheme.onSurfaceVariant,
            )
        }
        Spacer(Modifier.height(Spacing.sm))

        // Metadata strip
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
                    if (course.format == com.arka.mindle.model.CourseFormat.ARTICLE) R.string.course_format_article
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

        if (preview != null) {
            Spacer(Modifier.height(Spacing.lg))
            HorizontalDivider(color = app.line)
            Spacer(Modifier.height(Spacing.lg))

            // Feedback summary
            FeedbackSummary(average = preview.feedbackAverage, count = preview.feedbackCount, distribution = preview.feedbackDistribution)

            Spacer(Modifier.height(Spacing.lg))
            HorizontalDivider(color = app.line)
            Spacer(Modifier.height(Spacing.lg))

            // Overview
            Text(
                text = "Ringkasan",
                style = MaterialTheme.typography.titleMedium,
                color = scheme.onSurface,
            )
            Spacer(Modifier.height(Spacing.xs))
            OverviewText(text = preview.overview)

            // Discussion snippets
            if (preview.discussionSnippets.isNotEmpty()) {
                Spacer(Modifier.height(Spacing.lg))
                HorizontalDivider(color = app.line)
                Spacer(Modifier.height(Spacing.lg))
                Text(
                    text = "Diskusi",
                    style = MaterialTheme.typography.titleMedium,
                    color = scheme.onSurface,
                )
                Spacer(Modifier.height(Spacing.sm))
                preview.discussionSnippets.forEach { snippet ->
                    DiscussionSnippetRow(snippet = snippet)
                    Spacer(Modifier.height(Spacing.sm))
                }
            }
        }

        Spacer(Modifier.height(Spacing.xl))

        // CTA
        MindleButton(
            text = if (progress != null) {
                stringResource(R.string.course_resume, progress)
            } else {
                stringResource(R.string.cta_start)
            },
            onClick = onStartLearning,
            modifier = Modifier.fillMaxWidth(),
            tone = ButtonTone.Primary,
        )
        Spacer(Modifier.height(Spacing.hero))
    }
}

@Composable
private fun CourseCoverLarge(category: com.arka.mindle.model.CourseCategory, modifier: Modifier = Modifier) {
    val app = LocalAppColors.current
    val scheme = MaterialTheme.colorScheme
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(app.surfaceSunken),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(category.iconRes()),
            contentDescription = null,
            tint = scheme.onSurfaceVariant,
            modifier = Modifier.size(48.dp),
        )
    }
}

@Composable
private fun FeedbackSummary(average: Double, count: Int, distribution: List<Int>) {
    val scheme = MaterialTheme.colorScheme
    val app = LocalAppColors.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = String.format(java.util.Locale.forLanguageTag("id-ID"), "%.1f", average),
                style = MaterialTheme.typography.displayMedium,
                color = scheme.onSurface,
            )
            Text(
                text = "★",
                style = MaterialTheme.typography.bodyMedium,
                color = app.textTertiary,
            )
            Text(
                text = "$count ulasan",
                style = MaterialTheme.typography.bodySmall,
                color = app.textTertiary,
            )
        }
        Spacer(Modifier.width(Spacing.xl))
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
            val max = (distribution.maxOrNull() ?: 1).coerceAtLeast(1)
            (5 downTo 1).forEach { star ->
                val idx = 5 - star
                val value = distribution.getOrElse(idx) { 0 }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "$star",
                        style = NumM,
                        color = scheme.onSurfaceVariant,
                        modifier = Modifier.width(12.dp),
                    )
                    Spacer(Modifier.width(Spacing.xs))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(MaterialTheme.shapes.extraSmall)
                            .background(app.surfaceSunken),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(value.toFloat() / max)
                                .height(6.dp)
                                .background(scheme.onSurface, MaterialTheme.shapes.extraSmall),
                        )
                    }
                    Spacer(Modifier.width(Spacing.xs))
                    Text(
                        text = "$value",
                        style = NumM,
                        color = app.textTertiary,
                        modifier = Modifier.width(28.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun OverviewText(text: String) {
    var expanded by remember { mutableStateOf(false) }
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = if (expanded) Int.MAX_VALUE else 4,
        overflow = TextOverflow.Ellipsis,
    )
    if (!expanded) {
        Spacer(Modifier.height(Spacing.xs))
        Text(
            text = stringResource(R.string.preview_overview_more),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { expanded = true },
        )
    }
}

@Composable
private fun DiscussionSnippetRow(snippet: com.arka.mindle.model.DiscussionSnippet) {
    val scheme = MaterialTheme.colorScheme
    val app = LocalAppColors.current
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(app.surfaceSunken)
                .border(1.dp, app.line, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = snippet.authorName.take(1).uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = scheme.onSurfaceVariant,
            )
        }
        Spacer(Modifier.width(Spacing.sm))
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = snippet.authorName,
                    style = MaterialTheme.typography.bodySmall,
                    color = scheme.onSurface,
                )
                Spacer(Modifier.width(Spacing.xs))
                Text(
                    text = snippet.authorRole,
                    style = MaterialTheme.typography.labelSmall,
                    color = app.textTertiary,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = snippet.timestamp,
                    style = NumM,
                    color = app.textTertiary,
                )
            }
            Spacer(Modifier.height(Spacing.xxs))
            Text(
                text = snippet.content,
                style = MaterialTheme.typography.bodySmall,
                color = scheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

private fun formatRating(value: Double): String = String.format(java.util.Locale.forLanguageTag("id-ID"), "%.1f", value)

private fun formatLearners(count: Int): String {
    return if (count >= 1000) {
        String.format(java.util.Locale.forLanguageTag("id-ID"), "%.1fk", count / 1000f)
    } else {
        java.text.NumberFormat.getIntegerInstance(java.util.Locale.forLanguageTag("id-ID")).format(count)
    }
}
