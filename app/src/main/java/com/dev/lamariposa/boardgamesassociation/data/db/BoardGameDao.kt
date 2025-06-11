package com.dev.lamariposa.boardgamesassociation.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dev.lamariposa.boardgamesassociation.data.db.entities.BoardGameEntity

@Dao
interface BoardGameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBoardGame(game: BoardGameEntity): Long

    @Query("SELECT * FROM board_game WHERE id = :gameId")
    suspend fun getBoardGameById(gameId: Int): BoardGameEntity?

    @Query("SELECT * FROM board_game WHERE name LIKE :query")
    suspend fun searchBoardGames(query: String): List<BoardGameEntity>
}
