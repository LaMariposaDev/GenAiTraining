package com.dev.lamariposa.boardgamesassociation.domain.usecase

import com.dev.lamariposa.boardgamesassociation.domain.model.User
import com.dev.lamariposa.boardgamesassociation.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first

/**
 * Use case for getting the current authenticated user
 */
class GetCurrentUserUseCase(private val authRepository: AuthRepository) {
    
    /**
     * Gets the current authenticated user or null if not logged in
     */
    suspend operator fun invoke(): User? {
        return try {
            authRepository.getCurrentUser().first()
        } catch (e: Exception) {
            null
        }
    }
}
