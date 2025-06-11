package com.dev.lamariposa.boardgamesassociation.domain.repository

import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGame
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGameDetail

interface BoardGameRepository {
    suspend fun searchBoardGames(query: String): List<BoardGame>
    
    suspend fun getBoardGameDetails(id: Int): Result<BoardGameDetail>
    
    suspend fun saveBoardGame(boardGame: BoardGame): Long
    
    suspend fun getBoardGameById(id: Int): BoardGame?
}
