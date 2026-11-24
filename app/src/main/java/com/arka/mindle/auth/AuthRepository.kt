package com.arka.mindle.auth

import android.content.Context
import com.arka.mindle.R
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepository(private val context: Context) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val currentUser: FirebaseUser? get() = auth.currentUser

    suspend fun signIn(email: String, password: String): Result<FirebaseUser> = runCatching {
        auth.signInWithEmailAndPassword(email, password).await().user
            ?: throw IllegalStateException("Login gagal")
    }

    suspend fun signUp(displayName: String, email: String, password: String): Result<FirebaseUser> = runCatching {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        result.user?.updateProfile(
            com.google.firebase.auth.UserProfileChangeRequest.Builder()
                .setDisplayName(displayName).build()
        )?.await()
        result.user?.sendEmailVerification()?.await()
        result.user ?: throw IllegalStateException("Registrasi gagal")
    }

    suspend fun signInWithGoogle(): Result<FirebaseUser> = runCatching {
        // Generated from google-services.json web oauth_client (client_type 3).
        val webClientId = context.getString(R.string.default_web_client_id)
        val credentialManager = CredentialManager.create(context)
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(webClientId)
            .build()
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption).build()
        val result = try {
            credentialManager.getCredential(context, request)
        } catch (_: GetCredentialCancellationException) {
            throw IllegalStateException("Sign-in Google dibatalkan.")
        } catch (_: NoCredentialException) {
            throw IllegalStateException("Tidak ada akun Google di perangkat ini.")
        } catch (e: GetCredentialException) {
            throw IllegalStateException("Google Sign-In gagal (${e.type}). Periksa SHA-1 dan google-services.json.")
        }
        val googleCredential = result.credential as GoogleIdTokenCredential
        val firebaseCredential = GoogleAuthProvider.getCredential(googleCredential.idToken, null)
        auth.signInWithCredential(firebaseCredential).await().user
            ?: throw IllegalStateException("Login Google gagal")
    }

    fun signOut() = auth.signOut()

    suspend fun sendPasswordReset(email: String): Result<Unit> = runCatching {
        auth.sendPasswordResetEmail(email).await()
    }
}