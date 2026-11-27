package com.arka.mindle.ui.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arka.mindle.R
import com.arka.mindle.data.CourseRepository
import com.arka.mindle.data.MockCourseRepository
import com.arka.mindle.model.CourseCategory
import com.arka.mindle.ui.components.CompactCourseCard
import com.arka.mindle.ui.components.CourseCard
import com.arka.mindle.ui.components.CoursePreviewSheet
import com.arka.mindle.ui.components.labelRes
import com.arka.mindle.ui.theme.LocalAppColors
import com.arka.mindle.ui.theme.Spacing

private enum class SortMode { RECENT, POPULAR }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    isSignedIn: Boolean,
    onCourseClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    repository: CourseRepository = remember { MockCourseRepository() },
) {
    val scheme = MaterialTheme.colorScheme
    var selectedCategory by rememberSaveable { mutableStateOf<CourseCategory?>(null) }
    var sortMode by rememberSaveable { mutableStateOf(SortMode.RECENT) }
    var previewCourse by remember { mutableStateOf<com.arka.mindle.model.Course?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val feed by repository.observeFeed().collectAsState(initial = emptyList())
    val continueLearning by repository.observeContinueLearning().collectAsState(initial = emptyList())

    val courses = remember(feed, selectedCategory, sortMode) {
        val filtered = if (selectedCategory == null) feed
        else feed.filter { it.category == selectedCategory }
        if (sortMode == SortMode.POPULAR) filtered.sortedByDescending { it.learnerCount } else filtered
    }

    LazyColumn(
        modifier = modifier.fillMaxSize().background(scheme.background),
        contentPadding = PaddingValues(bottom = Spacing.xl),
    ) {
        item {
            ExploreTopBar()
        }
        item {
            CategoryRow(
                selected = selectedCategory,
                onSelect = { selectedCategory = it },
            )
        }
        if (isSignedIn && continueLearning.isNotEmpty()) {
            item {
                SectionLabel(text = stringResource(R.string.explore_continue_learning))
                Column(
                    modifier = Modifier.padding(horizontal = Spacing.screenHorizontal),
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm),
                ) {
                    continueLearning.forEach { course ->
                        CompactCourseCard(course = course, onClick = { previewCourse = course })
                    }
                }
                Spacer(Modifier.height(Spacing.lg))
            }
        }
        item {
            SortRow(sortMode = sortMode, onSortChange = { sortMode = it })
        }
        itemsIndexed(courses, key = { _, c -> c.id }) { index, course ->
            Column(modifier = Modifier.padding(horizontal = Spacing.screenHorizontal)) {
                if (index > 0 && index % 6 == 0) {
                    EditorialModule()
                    Spacer(Modifier.height(Spacing.md))
                }
                CourseCard(course = course, onClick = { previewCourse = course })
                Spacer(Modifier.height(Spacing.md))
            }
        }
    }

    previewCourse?.let { course ->
        ModalBottomSheet(
            onDismissRequest = { previewCourse = null },
            sheetState = sheetState,
            containerColor = scheme.surface,
        ) {
            CoursePreviewSheet(
                course = course,
                onStartLearning = {
                    previewCourse = null
                    onCourseClick(course.id)
                },
            )
        }
    }
}

@Composable
private fun ExploreTopBar() {
    val scheme = MaterialTheme.colorScheme
    val app = LocalAppColors.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(scheme.background)
            .padding(horizontal = Spacing.screenHorizontal, vertical = Spacing.lg),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displaySmall,
                color = scheme.onBackground,
                modifier = Modifier.weight(1f),
            )
            Icon(
                painter = painterResource(R.drawable.ic_notifications),
                contentDescription = stringResource(R.string.nav_notifications),
                tint = scheme.onSurface,
                modifier = Modifier.size(24.dp),
            )
        }
        Spacer(Modifier.height(Spacing.lg))
        SearchPill(onClick = { /* TODO: open search */ })
    }
}

@Composable
private fun SearchPill(onClick: () -> Unit) {
    val scheme = MaterialTheme.colorScheme
    val app = LocalAppColors.current
    val shape = androidx.compose.foundation.shape.RoundedCornerShape(50)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Spacing.touchTarget)
            .clip(shape)
            .border(1.dp, app.line, shape)
            .background(scheme.surface, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = Spacing.xl),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = null,
            tint = app.textTertiary,
            modifier = Modifier.size(20.dp),
        )
        Spacer(Modifier.width(Spacing.md))
        Text(
            text = stringResource(R.string.explore_search_placeholder),
            style = MaterialTheme.typography.bodyMedium,
            color = app.textTertiary,
        )
    }
}

@Composable
private fun CategoryRow(
    selected: CourseCategory?,
    onSelect: (CourseCategory?) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = Spacing.screenHorizontal, vertical = Spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(Spacing.md),
    ) {
        FilterChip(
            label = stringResource(R.string.category_all),
            selected = selected == null,
            onClick = { onSelect(null) },
        )
        CourseCategory.entries.forEach { category ->
            FilterChip(
                label = stringResource(category.labelRes()),
                selected = selected == category,
                onClick = { onSelect(if (selected == category) null else category) },
            )
        }
    }
}

@Composable
private fun FilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
    val scheme = MaterialTheme.colorScheme
    val app = LocalAppColors.current
    val shape = MaterialTheme.shapes.extraSmall
    Box(
        modifier = Modifier
            .clip(shape)
            .border(1.dp, if (selected) app.lineStrong else app.line, shape)
            .background(if (selected) scheme.surfaceVariant else scheme.surface, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = Spacing.lg, vertical = Spacing.sm),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = if (selected) scheme.onSurface else scheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun SortRow(sortMode: SortMode, onSortChange: (SortMode) -> Unit) {
    val scheme = MaterialTheme.colorScheme
    val app = LocalAppColors.current
    Row(
        modifier = Modifier.padding(horizontal = Spacing.screenHorizontal, vertical = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SortText(
            text = stringResource(R.string.explore_sort_recent),
            selected = sortMode == SortMode.RECENT,
            onClick = { onSortChange(SortMode.RECENT) },
        )
        Text(
            text = " / ",
            style = MaterialTheme.typography.bodySmall,
            color = app.textTertiary,
        )
        SortText(
            text = stringResource(R.string.explore_sort_popular),
            selected = sortMode == SortMode.POPULAR,
            onClick = { onSortChange(SortMode.POPULAR) },
        )
    }
}

@Composable
private fun SortText(text: String, selected: Boolean, onClick: () -> Unit) {
    val scheme = MaterialTheme.colorScheme
    val app = LocalAppColors.current
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = if (selected) scheme.onSurface else app.textTertiary,
        modifier = Modifier.clickable(onClick = onClick),
    )
}

@Composable
private fun SectionLabel(text: String) {
    val app = LocalAppColors.current
    Column(modifier = Modifier.padding(horizontal = Spacing.screenHorizontal, vertical = Spacing.md)) {
        Text(text = text.uppercase(), style = MaterialTheme.typography.labelSmall, color = app.textTertiary)
        Spacer(Modifier.height(Spacing.xs))
    }
}

@Composable
private fun EditorialModule() {
    val scheme = MaterialTheme.colorScheme
    val app = LocalAppColors.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(scheme.surfaceVariant)
            .padding(Spacing.lg),
    ) {
        Text(
            text = stringResource(R.string.explore_editorial_volunteers),
            style = MaterialTheme.typography.titleMedium,
            color = scheme.onSurface,
        )
        Spacer(Modifier.height(Spacing.xs))
        Text(
            text = stringResource(R.string.explore_editorial_volunteers_body),
            style = MaterialTheme.typography.bodyMedium,
            color = scheme.onSurfaceVariant,
        )
    }
}
