package com.dev.lamariposa.boardgamesassociation.data.repository

import com.dev.lamariposa.boardgamesassociation.data.api.BoardGameApiService
import com.dev.lamariposa.boardgamesassociation.data.api.mapper.toDomainModel
import com.dev.lamariposa.boardgamesassociation.data.db.BoardGameDao
import com.dev.lamariposa.boardgamesassociation.data.network.Result
import com.dev.lamariposa.boardgamesassociation.data.network.safeApiCall
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGame
import com.dev.lamariposa.boardgamesassociation.domain.repository.BoardGameRepository

class BoardGameRepositoryImpl(
    private val api: BoardGameApiService,
    private val dao: BoardGameDao
) : BoardGameRepository {
    override suspend fun searchBoardGames(query: String): List<BoardGame> {
        val result = safeApiCall { api.searchBoardGames(query) }
        
        return when (result) {
            is Result.Success -> {
                result.data.items?.mapNotNull { 
                    if (it.name?.value != null && it.id != null) {
                        it.toDomainModel()
                    } else {
                        null
                    }
                } ?: emptyList()
            }
            is Result.Error -> {
                // For now, just return empty list on error
                // In a real app, we would handle errors more gracefully
                emptyList()
            }
            is Result.Loading -> emptyList()
        }
    }
}
