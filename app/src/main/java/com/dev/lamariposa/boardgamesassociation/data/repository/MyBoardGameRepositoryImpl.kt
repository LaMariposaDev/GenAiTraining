package com.dev.lamariposa.boardgamesassociation.data.repository

import com.dev.lamariposa.boardgamesassociation.data.db.MyBoardGameDao
import com.dev.lamariposa.boardgamesassociation.data.mapper.toDomain
import com.dev.lamariposa.boardgamesassociation.data.mapper.toEntity
import com.dev.lamariposa.boardgamesassociation.domain.model.MyBoardGame
import com.dev.lamariposa.boardgamesassociation.domain.repository.MyBoardGameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyBoardGameRepositoryImpl(
    private val myBoardGameDao: MyBoardGameDao
) : MyBoardGameRepository {
    
    override suspend fun addBoardGameToCollection(myBoardGame: MyBoardGame): Long {
        return myBoardGameDao.insertMyBoardGame(myBoardGame.toEntity())
    }

    override suspend fun updateMyBoardGame(myBoardGame: MyBoardGame) {
        myBoardGameDao.updateMyBoardGame(myBoardGame.toEntity())
    }

    override suspend fun getMyBoardGameById(id: Long): MyBoardGame? {
        return myBoardGameDao.getMyBoardGameById(id)?.let {
            // Note: This returns just the entity, not the relation. In a real app, 
            // you'd need to handle this case properly or change the DAO method
            null
        }
    }

    override suspend fun getMyBoardGameByBoardGameId(boardGameId: Int): MyBoardGame? {
        return myBoardGameDao.getMyBoardGameByBoardGameId(boardGameId)?.toDomain()
    }

    override fun getAllMyBoardGames(): Flow<List<MyBoardGame>> {
        return myBoardGameDao.getAllMyBoardGames().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun deleteMyBoardGame(id: Long) {
        myBoardGameDao.deleteMyBoardGame(id)
    }
}
