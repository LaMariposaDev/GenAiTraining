package com.dev.lamariposa.boardgamesassociation.data.repository

import com.dev.lamariposa.boardgamesassociation.data.api.BoardGameApiService
import com.dev.lamariposa.boardgamesassociation.data.api.mapper.toDomainModel
import com.dev.lamariposa.boardgamesassociation.data.db.BoardGameDao
import com.dev.lamariposa.boardgamesassociation.data.mapper.toDomain
import com.dev.lamariposa.boardgamesassociation.data.mapper.toEntity
import com.dev.lamariposa.boardgamesassociation.data.network.Result
import com.dev.lamariposa.boardgamesassociation.data.network.safeApiCall
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGame
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGameDetail
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
    
    override suspend fun getBoardGameDetails(id: Int): kotlin.Result<BoardGameDetail> {
        return try {
            val result = safeApiCall { api.getBoardGameDetails(id.toString()) }
            
            when (result) {
                is Result.Success -> {
                    val boardGameDetail = result.data.items?.firstOrNull()?.let {
                        com.dev.lamariposa.boardgamesassociation.data.api.mapper.toDomainModel(it)
                    }
                    
                    if (boardGameDetail != null) {
                        kotlin.Result.success(boardGameDetail)
                    } else {
                        kotlin.Result.failure(Exception("Board game details not found"))
                    }
                }
                is Result.Error -> {
                    kotlin.Result.failure(result.exception)
                }
                is Result.Loading -> {
                    kotlin.Result.failure(Exception("Loading state should not be returned"))
                }
            }
        } catch (e: Exception) {
            kotlin.Result.failure(e)
        }
    }
    
    override suspend fun saveBoardGame(boardGame: BoardGame): Long {
        val entity = boardGame.toEntity()
        return dao.insertBoardGame(entity).toLong()
    }
    
    override suspend fun getBoardGameById(id: Int): BoardGame? {
        val entity = dao.getBoardGameById(id)
        return entity?.toDomain()
    }
}
