package com.dev.lamariposa.boardgamesassociation.domain.repository

import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGame

interface BoardGameRepository {
    suspend fun searchBoardGames(query: String): List<BoardGame>
}
