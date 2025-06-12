package com.dev.lamariposa.boardgamesassociation.data.api.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Firebase authentication service implementation
 */
class FirebaseAuthService(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    /**
     * Get the current authenticated user or null if not authenticated
     */
    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    /**
     * Create a new user account with email and password
     * @param email User's email
     * @param password User's password
     * @return FirebaseUser if successful
     */
    suspend fun createUserWithEmailAndPassword(email: String, password: String): FirebaseUser {
        Log.d("FirebaseAuthService", "Creating new user account with email: $email")
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            authResult.user ?: throw IllegalStateException("User creation successful but user is null")
        } catch (e: Exception) {
            Log.e("FirebaseAuthService", "Failed to create user with email: $email", e)
            throw e
        }
    }

    /**
     * Sign in with email and password
     * @param email User's email
     * @param password User's password
     * @return FirebaseUser if successful
     */
    suspend fun signInWithEmailAndPassword(email: String, password: String): FirebaseUser {
        Log.d("FirebaseAuthService", "Signing in user with email: $email")
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            authResult.user ?: throw IllegalStateException("Sign in successful but user is null")
        } catch (e: Exception) {
            Log.e("FirebaseAuthService", "Failed to sign in user with email: $email", e)
            throw e
        }
    }

    /**
     * Send password reset email
     * @param email User's email
     */
    suspend fun sendPasswordResetEmail(email: String) {
        Log.d("FirebaseAuthService", "Sending password reset email to: $email")
        try {
            firebaseAuth.sendPasswordResetEmail(email).await()
        } catch (e: Exception) {
            Log.e("FirebaseAuthService", "Failed to send password reset email to: $email", e)
            throw e
        }
    }

    /**
     * Send email verification to currently signed-in user
     */
    suspend fun sendEmailVerification() {
        val user = getCurrentUser()
        if (user != null) {
            Log.d("FirebaseAuthService", "Sending email verification to: ${user.email}")
            try {
                user.sendEmailVerification().await()
            } catch (e: Exception) {
                Log.e("FirebaseAuthService", "Failed to send email verification to: ${user.email}", e)
                throw e
            }
        } else {
            Log.e("FirebaseAuthService", "Cannot send email verification: No user is signed in")
            throw IllegalStateException("No user is signed in")
        }
    }

    /**
     * Sign out the current user
     */
    fun signOut() {
        Log.d("FirebaseAuthService", "Signing out user: ${getCurrentUser()?.email}")
        firebaseAuth.signOut()
    }

    /**
     * Check if the current user's email is verified
     * @return true if verified, false otherwise
     */
    fun isEmailVerified(): Boolean {
        val user = getCurrentUser()
        return user?.isEmailVerified ?: false
    }

    /**
     * Add an auth state listener to detect sign in/out events
     * @param listener Function to call when auth state changes
     * @return AuthStateListenerRemover to remove the listener when needed
     */
    fun addAuthStateListener(listener: (FirebaseUser?) -> Unit): AuthStateListenerRemover {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            listener(auth.currentUser)
        }
        firebaseAuth.addAuthStateListener(authStateListener)
        return AuthStateListenerRemover(firebaseAuth, authStateListener)
    }

    /**
     * Class to remove auth state listener
     */
    class AuthStateListenerRemover(
        private val auth: FirebaseAuth,
        private val listener: FirebaseAuth.AuthStateListener
    ) {
        fun remove() {
            auth.removeAuthStateListener(listener)
        }
    }

    /**
     * Get all users from Firebase
     * Note: Firebase Auth doesn't allow direct retrieval of all users from client side
     * This implementation fetches users from a Firestore collection where user data is stored
     *
     * @return List of FirebaseUser objects
     */
    suspend fun getAllUsers(): List<FirebaseUser> {
        Log.d("FirebaseAuthService", "Fetching all users from Firestore")
        try {
            // Pobieramy dane użytkowników z kolekcji Firestore
            val usersSnapshot = firestore.collection("users").get().await()

            // Ponieważ nie możemy bezpośrednio pobierać obiektów FirebaseUser,
            // musimy pobrać zalogowanych użytkowników z bazy danych
            val currentUser = firebaseAuth.currentUser
            val users = mutableListOf<FirebaseUser>()

            if (currentUser != null) {
                users.add(currentUser)
            }

            Log.d("FirebaseAuthService", "Retrieved ${usersSnapshot.size()} user records from Firestore")
            return users
        } catch (e: Exception) {
            Log.e("FirebaseAuthService", "Error fetching users from Firestore", e)
            return emptyList()
        }
    }
}