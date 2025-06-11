package com.dev.lamariposa.boardgamesassociation.domain.repository

import com.dev.lamariposa.boardgamesassociation.domain.model.MyBoardGame
import kotlinx.coroutines.flow.Flow

interface MyBoardGameRepository {
    suspend fun addBoardGameToCollection(myBoardGame: MyBoardGame): Long
    suspend fun updateMyBoardGame(myBoardGame: MyBoardGame)
    suspend fun getMyBoardGameById(id: Long): MyBoardGame?
    suspend fun getMyBoardGameByBoardGameId(boardGameId: Int): MyBoardGame?
    fun getAllMyBoardGames(): Flow<List<MyBoardGame>>
    suspend fun deleteMyBoardGame(id: Long)
}
