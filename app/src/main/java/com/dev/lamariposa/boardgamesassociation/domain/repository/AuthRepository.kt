package com.dev.lamariposa.boardgamesassociation.domain.repository

import com.dev.lamariposa.boardgamesassociation.domain.model.AuthResult
import com.dev.lamariposa.boardgamesassociation.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for authentication operations
 */
interface AuthRepository {
    /**
     * Get the current authenticated user as a flow
     * @return Flow of User that emits when auth state changes
     */
    fun getCurrentUser(): Flow<User?>
    
    /**
     * Sign in with email and password
     * @param email User's email
     * @param password User's password
     * @return AuthResult indicating success or failure
     */
    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult
    
    /**
     * Create a new user account with email and password
     * @param email User's email
     * @param password User's password
     * @return AuthResult indicating success or failure
     */
    suspend fun createUserWithEmailAndPassword(email: String, password: String): AuthResult
    
    /**
     * Send password reset email
     * @param email User's email
     * @return true if email was sent successfully, false otherwise
     */
    suspend fun sendPasswordResetEmail(email: String): Boolean
    
    /**
     * Send email verification to currently signed-in user
     * @return true if email was sent successfully, false otherwise
     */
    suspend fun sendEmailVerification(): Boolean
    
    /**
     * Sign out the current user
     */
    fun signOut()
    
    /**
     * Check if the current user's email is verified
     * @return true if verified, false otherwise
     */
    fun isEmailVerified(): Boolean

    /**
     * Get all users from Firebase
     * @return List of all users in the system
     */
    suspend fun getAllUsers(): List<User>
}