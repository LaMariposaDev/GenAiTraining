package com.dev.lamariposa.boardgamesassociation.data.repository

import android.util.Log
import com.dev.lamariposa.boardgamesassociation.data.api.auth.FirebaseAuthService
import com.dev.lamariposa.boardgamesassociation.data.mapper.AuthMapper
import com.dev.lamariposa.boardgamesassociation.domain.model.AuthResult
import com.dev.lamariposa.boardgamesassociation.domain.model.User
import com.dev.lamariposa.boardgamesassociation.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Implementation of AuthRepository that uses Firebase Authentication
 */
class AuthRepositoryImpl(
    private val firebaseAuthService: FirebaseAuthService
) : AuthRepository {

    override fun getCurrentUser(): Flow<User?> = callbackFlow {
        Log.d("AuthRepositoryImpl", "Setting up auth state listener for current user flow")
        
        // Initial emission with current user
        val currentUser = AuthMapper.mapToDomainUser(firebaseAuthService.getCurrentUser())
        trySend(currentUser)
        
        // Set up listener for auth state changes
        val listenerRemover = firebaseAuthService.addAuthStateListener { firebaseUser ->
            val user = AuthMapper.mapToDomainUser(firebaseUser)
            Log.d("AuthRepositoryImpl", "Auth state changed. User: ${user?.email ?: "null"}")
            trySend(user)
        }
        
        // Clean up listener when flow collection stops
        awaitClose {
            Log.d("AuthRepositoryImpl", "Removing auth state listener")
            listenerRemover.remove()
        }
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult {
        Log.d("AuthRepositoryImpl", "Signing in with email and password")
        return try {
            val firebaseUser = firebaseAuthService.signInWithEmailAndPassword(email, password)
            val user = AuthMapper.mapToDomainUser(firebaseUser)
            if (user != null) {
                Log.d("AuthRepositoryImpl", "Sign in successful for user: ${user.email}")
                AuthResult.Success(user)
            } else {
                val exception = IllegalStateException("User is null after successful sign in")
                Log.e("AuthRepositoryImpl", "Sign in failed: User is null after successful sign in", exception)
                AuthResult.Error(exception)
            }
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Sign in failed", e)
            AuthResult.Error(e)
        }
    }

    override suspend fun createUserWithEmailAndPassword(email: String, password: String): AuthResult {
        Log.d("AuthRepositoryImpl", "Creating user with email and password")
        return try {
            val firebaseUser = firebaseAuthService.createUserWithEmailAndPassword(email, password)
            val user = AuthMapper.mapToDomainUser(firebaseUser)
            if (user != null) {
                Log.d("AuthRepositoryImpl", "User creation successful for: ${user.email}")
                AuthResult.Success(user)
            } else {
                val exception = IllegalStateException("User is null after successful creation")
                Log.e("AuthRepositoryImpl", "User creation failed: User is null after successful creation", exception)
                AuthResult.Error(exception)
            }
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "User creation failed", e)
            AuthResult.Error(e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Boolean {
        Log.d("AuthRepositoryImpl", "Sending password reset email to: $email")
        return try {
            firebaseAuthService.sendPasswordResetEmail(email)
            Log.d("AuthRepositoryImpl", "Password reset email sent successfully to: $email")
            true
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Failed to send password reset email", e)
            false
        }
    }

    override suspend fun sendEmailVerification(): Boolean {
        Log.d("AuthRepositoryImpl", "Sending email verification")
        return try {
            firebaseAuthService.sendEmailVerification()
            Log.d("AuthRepositoryImpl", "Email verification sent successfully")
            true
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Failed to send email verification", e)
            false
        }
    }

    override fun signOut() {
        Log.d("AuthRepositoryImpl", "Signing out user")
        firebaseAuthService.signOut()
    }

    override fun isEmailVerified(): Boolean {
        val isVerified = firebaseAuthService.isEmailVerified()
        Log.d("AuthRepositoryImpl", "Checking if email is verified: $isVerified")
        return isVerified
    }

    override suspend fun getAllUsers(): List<User> {
        Log.d("AuthRepositoryImpl", "Getting all users from Firebase")
        return try {
            val firebaseUsers = firebaseAuthService.getAllUsers()
            val users = firebaseUsers.mapNotNull { firebaseUser ->
                AuthMapper.mapToDomainUser(firebaseUser)
            }
            Log.d("AuthRepositoryImpl", "Successfully retrieved ${users.size} users from Firebase")
            users
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Failed to get users from Firebase", e)
            emptyList()
        }
    }
}
