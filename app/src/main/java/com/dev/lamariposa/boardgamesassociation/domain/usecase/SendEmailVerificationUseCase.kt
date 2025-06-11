package com.dev.lamariposa.boardgamesassociation.domain.usecase

import android.util.Log
import com.dev.lamariposa.boardgamesassociation.domain.repository.AuthRepository

/**
 * Use case for sending email verification
 */
class SendEmailVerificationUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): Boolean {
        Log.d("SendEmailVerification", "SendEmailVerificationUseCase - Executing")
        return authRepository.sendEmailVerification()
    }
}
