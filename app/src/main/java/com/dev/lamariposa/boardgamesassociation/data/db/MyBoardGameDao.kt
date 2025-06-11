package com.dev.lamariposa.boardgamesassociation.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.dev.lamariposa.boardgamesassociation.data.db.entities.MyBoardGameEntity
import com.dev.lamariposa.boardgamesassociation.data.db.relations.MyBoardGameWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface MyBoardGameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMyBoardGame(myBoardGame: MyBoardGameEntity): Long

    @Update
    suspend fun updateMyBoardGame(myBoardGame: MyBoardGameEntity)

    @Query("SELECT * FROM my_board_game WHERE id = :id")
    suspend fun getMyBoardGameById(id: Long): MyBoardGameEntity?

    @Transaction
    @Query("SELECT * FROM my_board_game ORDER BY dateAdded DESC")
    fun getAllMyBoardGames(): Flow<List<MyBoardGameWithDetails>>

    @Transaction
    @Query("SELECT * FROM my_board_game WHERE boardGameId = :boardGameId")
    suspend fun getMyBoardGameByBoardGameId(boardGameId: Int): MyBoardGameWithDetails?

    @Query("DELETE FROM my_board_game WHERE id = :id")
    suspend fun deleteMyBoardGame(id: Long)
}
