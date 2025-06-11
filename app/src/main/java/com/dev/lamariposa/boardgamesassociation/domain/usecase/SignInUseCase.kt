package com.dev.lamariposa.boardgamesassociation.domain.usecase

import android.util.Log
import com.dev.lamariposa.boardgamesassociation.domain.model.AuthResult
import com.dev.lamariposa.boardgamesassociation.domain.repository.AuthRepository

/**
 * Use case for signing in with email and password
 */
class SignInUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): AuthResult {
        Log.d("SignInUseCase", "Executing with email: $email")
        return authRepository.signInWithEmailAndPassword(email, password)
    }
}
