package com.arka.mindle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arka.mindle.auth.AuthRepository
import com.arka.mindle.auth.AuthScreen
import com.arka.mindle.auth.AuthViewModel
import com.arka.mindle.ui.shell.AppShell
import com.arka.mindle.ui.theme.MindleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MindleApp()
                }
            }
        }
    }
}

private object Routes {
    const val SHELL = "shell"
    const val AUTH = "auth"
}

@Composable
private fun MindleApp() {
    val context = LocalContext.current
    val repository = remember { AuthRepository(context) }
    val navController = rememberNavController()
    var isSignedIn by remember { mutableStateOf(repository.currentUser != null) }

    NavHost(navController = navController, startDestination = Routes.SHELL) {
        composable(Routes.SHELL) {
            AppShell(
                isSignedIn = isSignedIn,
                onRequestSignIn = { navController.navigate(Routes.AUTH) },
                onSignOut = {
                    repository.signOut()
                    isSignedIn = false
                },
            )
        }
        composable(Routes.AUTH) {
            val authViewModel: AuthViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return AuthViewModel(repository) as T
                    }
                },
            )
            AuthScreen(
                viewModel = authViewModel,
                onAuthSuccess = {
                    isSignedIn = true
                    navController.popBackStack(Routes.SHELL, inclusive = false)
                },
                onContinueAsGuest = {
                    navController.popBackStack(Routes.SHELL, inclusive = false)
                },
            )
        }
    }
}
