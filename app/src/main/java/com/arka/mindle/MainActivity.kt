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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arka.mindle.auth.AuthRepository
import com.arka.mindle.auth.AuthScreen
import com.arka.mindle.auth.AuthViewModel
import com.arka.mindle.ui.theme.MindleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindleTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AuthRoute()
                }
            }
        }
    }
}

@Composable
private fun AuthRoute() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val repository = remember { AuthRepository(context) }
    val viewModel: AuthViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository) as T
        }
    })
    var authed by remember { mutableStateOf(repository.currentUser != null) }

    if (authed) {
        Greeting(name = "Android")
    } else {
        AuthScreen(viewModel = viewModel, onAuthSuccess = { authed = true })
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    androidx.compose.material3.Text(text = "Hello $name!", modifier = modifier)
}