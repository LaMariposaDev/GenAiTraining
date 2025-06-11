package com.dev.lamariposa.boardgamesassociation.domain.model

/**
 * User model representing an authenticated user
 */
data class User(
    val id: String,
    val email: String,
    val displayName: String?,
    val isEmailVerified: Boolean
)

/**
 * Authentication result model
 */
sealed class AuthResult {
    data class Success(val user: User) : AuthResult()
    data class Error(val exception: Exception) : AuthResult()
}

/**
 * User session state
 */
enum class AuthState {
    AUTHENTICATED,
    UNAUTHENTICATED,
    UNVERIFIED_EMAIL,
    LOADING
}
