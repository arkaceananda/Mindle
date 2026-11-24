package com.arka.mindle.ui.shell

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.arka.mindle.R
import com.arka.mindle.ui.components.BottomNavItem
import com.arka.mindle.ui.components.ButtonTone
import com.arka.mindle.ui.components.MindleBottomBar
import com.arka.mindle.ui.components.MindleButton
import com.arka.mindle.ui.explore.ExploreScreen
import com.arka.mindle.ui.mylearning.MyLearningScreen
import com.arka.mindle.ui.profile.ProfileScreen
import com.arka.mindle.ui.theme.LocalAppColors
import com.arka.mindle.ui.theme.Spacing

private enum class ShellTab { EXPLORE, MY_LEARNING, PROFILE }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppShell(
    isSignedIn: Boolean,
    onRequestSignIn: () -> Unit,
    onSignOut: () -> Unit,
) {
    var selectedTab by rememberSaveable { mutableStateOf(ShellTab.EXPLORE) }
    var showSignInSheet by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val items = listOf(
        BottomNavItem(
            label = stringResource(R.string.nav_explore),
            icon = painterResource(R.drawable.ic_explore),
            selectedIcon = painterResource(R.drawable.ic_explore_filled),
        ),
        BottomNavItem(
            label = stringResource(R.string.nav_my_learning),
            icon = painterResource(R.drawable.ic_learning),
            selectedIcon = painterResource(R.drawable.ic_learning_filled),
        ),
        BottomNavItem(
            label = stringResource(R.string.nav_profile),
            icon = painterResource(R.drawable.ic_profile),
            selectedIcon = painterResource(R.drawable.ic_profile_filled),
        ),
    )
    val selectedIndex = when (selectedTab) {
        ShellTab.EXPLORE -> 0
        ShellTab.MY_LEARNING -> 1
        ShellTab.PROFILE -> 2
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            MindleBottomBar(
                items = items,
                selectedIndex = selectedIndex,
                modifier = Modifier.navigationBarsPadding(),
                onSelect = { index ->
                    val tab = ShellTab.entries[index]
                    val gated = tab != ShellTab.EXPLORE
                    if (gated && !isSignedIn) {
                        showSignInSheet = true
                    } else {
                        selectedTab = tab
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            when (selectedTab) {
                ShellTab.EXPLORE -> ExploreScreen(
                    isSignedIn = isSignedIn,
                    onCourseClick = { /* TODO: course preview */ },
                )
                ShellTab.MY_LEARNING -> MyLearningScreen()
                ShellTab.PROFILE -> ProfileScreen(isSignedIn = isSignedIn, onSignOut = onSignOut)
            }
        }
    }

    if (showSignInSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSignInSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            SignInGateSheet(
                onSignIn = {
                    showSignInSheet = false
                    onRequestSignIn()
                },
            )
        }
    }
}

@Composable
private fun SignInGateSheet(onSignIn: () -> Unit) {
    val app = LocalAppColors.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.xl, vertical = Spacing.lg),
    ) {
        Text(
            text = stringResource(R.string.gated_sign_in_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.height(Spacing.sm))
        Text(
            text = stringResource(R.string.gated_sign_in_body),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(Spacing.xl))
        MindleButton(
            text = stringResource(R.string.gated_sign_in_action),
            onClick = onSignIn,
            modifier = Modifier.fillMaxWidth(),
            tone = ButtonTone.Primary,
        )
        Spacer(Modifier.height(Spacing.hero))
    }
}
