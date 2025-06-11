package com.dev.lamariposa.boardgamesassociation.domain.usecase

import android.util.Log
import com.dev.lamariposa.boardgamesassociation.domain.repository.AuthRepository

/**
 * Use case for signing out
 */
class SignOutUseCase(private val authRepository: AuthRepository) {
    operator fun invoke() {
        Log.d("SignOutUseCase", "Executing")
        authRepository.signOut()
    }
}
