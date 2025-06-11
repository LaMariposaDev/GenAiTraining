package com.dev.lamariposa.boardgamesassociation.domain.usecase

import android.util.Log
import com.dev.lamariposa.boardgamesassociation.domain.model.User
import com.dev.lamariposa.boardgamesassociation.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for getting the current user as a flow
 */
class GetCurrentUserUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(): Flow<User?> {
        Log.d("GetCurrentUserUseCase", "Executing")
        return authRepository.getCurrentUser()
    }
}
