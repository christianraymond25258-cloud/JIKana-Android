package com.jikana.app.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class AuthState(
    val isLoading: Boolean = false,
    val user: FirebaseUser? = null,
    val error: String? = null,
    val isSuccess: Boolean = false
)

class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    init {
        _authState.value = AuthState(user = auth.currentUser)
    }

    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("850695277832-vp2916h1n0lmnfl4f2k9klrcdp9uda8a.apps.googleusercontent.com")
            .requestEmail()
            .requestProfile()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(credential).await()
                val user = auth.currentUser
                user?.let {
                    val doc = firestore.collection("users").document(it.uid).get().await()
                    if (!doc.exists()) {
                        firestore.collection("users").document(it.uid).set(
                            mapOf(
                                "name" to (it.displayName ?: "Learner"),
                                "email" to (it.email ?: ""),
                                "createdAt" to System.currentTimeMillis(),
                                "hiraganaProgress" to 0,
                                "katakanaProgress" to 0,
                                "kanjiProgress" to 0,
                                "streak" to 0,
                                "lastStudied" to 0L
                            )
                        ).await()
                    }
                }
                _authState.value = AuthState(user = user, isSuccess = true)
            } catch (e: Exception) {
                _authState.value = AuthState(error = e.message ?: "Google Sign-In failed")
            }
        }
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthState(error = "Please fill in all fields")
            return
        }
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                _authState.value = AuthState(user = auth.currentUser, isSuccess = true)
            } catch (e: Exception) {
                _authState.value = AuthState(error = e.message ?: "Login failed")
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _authState.value = AuthState(error = "Please fill in all fields")
            return
        }
        if (password.length < 6) {
            _authState.value = AuthState(error = "Password must be at least 6 characters")
            return
        }
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                auth.currentUser?.let { user ->
                    firestore.collection("users").document(user.uid).set(
                        mapOf(
                            "name" to name,
                            "email" to email,
                            "createdAt" to System.currentTimeMillis(),
                            "hiraganaProgress" to 0,
                            "katakanaProgress" to 0,
                            "kanjiProgress" to 0,
                            "streak" to 0,
                            "lastStudied" to 0L
                        )
                    ).await()
                }
                _authState.value = AuthState(user = auth.currentUser, isSuccess = true)
            } catch (e: Exception) {
                _authState.value = AuthState(error = e.message ?: "Registration failed")
            }
        }
    }

    fun signOut() {
        auth.signOut()
        // Full reset — isSuccess must be false so login works again
        _authState.value = AuthState(
            user = null,
            isSuccess = false,
            isLoading = false,
            error = null
        )
    }

    fun clearError() {
        _authState.value = _authState.value.copy(error = null)
    }
}
