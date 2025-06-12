package com.dev.lamariposa.boardgamesassociation.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.lamariposa.boardgamesassociation.domain.model.AuthResult
import com.dev.lamariposa.boardgamesassociation.domain.model.AuthState
import com.dev.lamariposa.boardgamesassociation.domain.model.User
import com.dev.lamariposa.boardgamesassociation.domain.usecase.AuthUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * ViewModel for authentication-related operations
 */
class AuthViewModel(private val authUseCases: AuthUseCases) : ViewModel() {

    private val _authState = MutableLiveData<AuthState>(AuthState.LOADING)
    val authState: LiveData<AuthState> = _authState

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private val _authError = MutableLiveData<String?>()
    val authError: LiveData<String?> = _authError

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        Log.d("AuthViewModel", "AuthViewModel initialized")
        observeCurrentUser()
    }

    private fun observeCurrentUser() {
        viewModelScope.launch {
        Log.d("AuthViewModel", "Setting up current user observation")
        authUseCases.getCurrentUser.invoke().let { user ->
            _user.value = user
            
            if (user != null) {
                if (user.isEmailVerified) {
                    _authState.value = AuthState.AUTHENTICATED
                    Log.d("AuthViewModel", "User authenticated and verified: ${user.email}")
                } else {
                    _authState.value = AuthState.UNVERIFIED_EMAIL
                    Log.d("AuthViewModel", "User authenticated but email not verified: ${user.email}")
                }
            } else {
                _authState.value = AuthState.UNAUTHENTICATED
                Log.d("AuthViewModel", "User not authenticated")
            }
        }
        }
    }

    /**
     * Sign in with email and password
     */
    fun signIn(email: String, password: String) {
        Log.d("AuthViewModel", "Attempting sign in with email: $email")
        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null
            
            when (val result = authUseCases.signIn(email, password)) {
                is AuthResult.Success -> {
                    Log.d("AuthViewModel", "Sign in successful for user: ${result.user.email}")
                    // Success is handled by the user flow
                }
                is AuthResult.Error -> {
                    Log.e("AuthViewModel", "Sign in failed", result.exception)
                    _authError.value = result.exception.message ?: "Sign in failed"
                    _authState.value = AuthState.UNAUTHENTICATED
                }
            }
            
            _isLoading.value = false
        }
    }

    /**
     * Create a new user account with email and password
     */
    fun createAccount(email: String, password: String) {
        Log.d("AuthViewModel", "Attempting to create account with email: $email")
        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null
            
            when (val result = authUseCases.createUser(email, password)) {
                is AuthResult.Success -> {
                    Log.d("AuthViewModel", "Account created successfully for user: ${result.user.email}")
                    // Send email verification
                    sendEmailVerification()
                }
                is AuthResult.Error -> {
                    Log.e("AuthViewModel", "Account creation failed", result.exception)
                    _authError.value = result.exception.message ?: "Account creation failed"
                    _authState.value = AuthState.UNAUTHENTICATED
                }
            }
            
            _isLoading.value = false
        }
    }

    /**
     * Send email verification to current user
     */
    fun sendEmailVerification() {
        Log.d("AuthViewModel", "Sending email verification")
        viewModelScope.launch {
            val success = authUseCases.sendEmailVerification()
            if (success) {
                Log.d("AuthViewModel", "Email verification sent successfully")
            } else {
                Log.e("AuthViewModel", "Failed to send email verification")
                _authError.value = "Failed to send verification email"
            }
        }
    }

    /**
     * Send password reset email
     */
    fun sendPasswordResetEmail(email: String) {
        Log.d("AuthViewModel", "Sending password reset email to: $email")
        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null
            
            val success = authUseCases.sendPasswordResetEmail(email)
            
            if (success) {
                Log.d("AuthViewModel", "Password reset email sent successfully to: $email")
            } else {
                Log.e("AuthViewModel", "Failed to send password reset email to: $email")
                _authError.value = "Failed to send password reset email"
            }
            
            _isLoading.value = false
        }
    }

    /**
     * Sign out the current user
     */
    fun signOut() {
        Log.d("AuthViewModel", "Signing out user")
        authUseCases.signOut()
        // State change is handled by the user flow
    }

    /**
     * Clear any error messages
     */
    fun clearError() {
        _authError.value = null
    }
}
