package com.dev.lamariposa.boardgamesassociation.domain.usecase

import android.util.Log
import com.dev.lamariposa.boardgamesassociation.domain.repository.AuthRepository

/**
 * Use case for sending password reset email
 */
class SendPasswordResetEmailUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String): Boolean {
        Log.d("SendPasswordResetEmail", "SendPasswordResetEmailUseCase - Executing with email: $email")
        return authRepository.sendPasswordResetEmail(email)
    }
}
