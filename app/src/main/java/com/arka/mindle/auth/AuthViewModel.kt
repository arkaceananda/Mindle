package com.arka.mindle.auth

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

data class AuthForm(
    val isRegister: Boolean = false,
    val displayName: String = "",
    val email: String = "",
    val password: String = "",
    val showPassword: Boolean = false
)

sealed interface AuthUiState {
    val form: AuthForm

    data class Idle(override val form: AuthForm = AuthForm()) : AuthUiState
    data class Loading(override val form: AuthForm) : AuthUiState
    data class Error(override val form: AuthForm, val message: String) : AuthUiState
    data class Success(override val form: AuthForm, val user: FirebaseUser) : AuthUiState
}

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    var state by mutableStateOf<AuthUiState>(AuthUiState.Idle())
        private set

    private fun updateForm(transform: (AuthForm) -> AuthForm) {
        state = when (val s = state) {
            is AuthUiState.Idle -> AuthUiState.Idle(transform(s.form))
            is AuthUiState.Loading -> AuthUiState.Loading(transform(s.form))
            is AuthUiState.Error -> AuthUiState.Error(transform(s.form), s.message)
            is AuthUiState.Success -> AuthUiState.Success(transform(s.form), s.user)
        }
    }

    fun toggleMode() = updateForm { it.copy(isRegister = !it.isRegister) }
    fun onDisplayNameChange(v: String) = updateForm { it.copy(displayName = v) }
    fun onEmailChange(v: String) = updateForm { it.copy(email = v) }
    fun onPasswordChange(v: String) = updateForm { it.copy(password = v) }
    fun togglePassword() = updateForm { it.copy(showPassword = !it.showPassword) }

    fun submit() {
        val form = state.form
        val error = when {
            form.email.isBlank() || form.password.isBlank() -> "Email dan password wajib diisi."
            form.isRegister && form.displayName.isBlank() -> "Nama wajib diisi."
            else -> null
        }
        if (error != null) {
            state = AuthUiState.Error(form, error)
            return
        }
        state = AuthUiState.Loading(form)
        viewModelScope.launch {
            val result = if (form.isRegister) {
                repository.signUp(form.displayName, form.email, form.password)
            } else {
                repository.signIn(form.email, form.password)
            }
            result.onSuccess { user ->
                state = AuthUiState.Success(form, user)
            }.onFailure { e ->
                state = AuthUiState.Error(form, e.message ?: "Terjadi masalah. Coba lagi.")
            }
        }
    }

    fun signInWithGoogle(context: Context) {
        state = AuthUiState.Loading(state.form)
        viewModelScope.launch {
            repository.signInWithGoogle().onSuccess { user ->
                state = AuthUiState.Success(state.form, user)
            }.onFailure { e ->
                state = AuthUiState.Error(state.form, e.message ?: "Login Google gagal.")
            }
        }
    }

    fun signOut() {
        repository.signOut()
        state = AuthUiState.Idle()
    }

    val isLoading: Boolean get() = state is AuthUiState.Loading
    val errorMessage: String? get() = (state as? AuthUiState.Error)?.message
    val user: FirebaseUser? get() = (state as? AuthUiState.Success)?.user ?: repository.currentUser
}