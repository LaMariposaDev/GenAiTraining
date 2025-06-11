package com.dev.lamariposa.boardgamesassociation.domain.usecase

import android.util.Log
import com.dev.lamariposa.boardgamesassociation.domain.repository.AuthRepository

/**
 * Use case for checking if the current user's email is verified
 */
class IsEmailVerifiedUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(): Boolean {
        Log.d("IsEmailVerifiedUseCase", "Executing")
        return authRepository.isEmailVerified()
    }
}
