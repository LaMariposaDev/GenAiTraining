package com.dev.lamariposa.boardgamesassociation.data.repository

import com.dev.lamariposa.boardgamesassociation.data.api.BoardGameApiService
import com.dev.lamariposa.boardgamesassociation.data.db.BoardGameDao
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGame
import com.dev.lamariposa.boardgamesassociation.domain.repository.BoardGameRepository

class BoardGameRepositoryImpl(
    private val api: BoardGameApiService,
    private val dao: BoardGameDao
) : BoardGameRepository {
    override suspend fun searchBoardGames(query: String): List<BoardGame> {
        // Example: fetch from API, map to domain, save/cache, etc.
        return emptyList()
    }
}
