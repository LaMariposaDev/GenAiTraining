package com.dev.lamariposa.boardgamesassociation.domain.usecase

import android.util.Log
import com.dev.lamariposa.boardgamesassociation.domain.model.AuthResult
import com.dev.lamariposa.boardgamesassociation.domain.repository.AuthRepository

/**
 * Use case for creating a new user with email and password
 */
class CreateUserUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): AuthResult {
        Log.d("CreateUserUseCase", "Executing with email: $email")
        return authRepository.createUserWithEmailAndPassword(email, password)
    }
}
