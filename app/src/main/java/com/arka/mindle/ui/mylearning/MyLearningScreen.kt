package com.arka.mindle.ui.mylearning

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.arka.mindle.R
import com.arka.mindle.ui.theme.LocalAppColors
import com.arka.mindle.ui.theme.Spacing

@Composable
fun MyLearningScreen(modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val app = LocalAppColors.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(scheme.background)
            .padding(horizontal = Spacing.screenHorizontal, vertical = Spacing.hero),
        verticalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        Text(
            text = stringResource(R.string.my_learning_title),
            style = MaterialTheme.typography.displayMedium,
            color = scheme.onBackground,
        )
        Text(
            text = stringResource(R.string.placeholder_coming_soon),
            style = MaterialTheme.typography.bodyMedium,
            color = app.textTertiary,
        )
    }
}
