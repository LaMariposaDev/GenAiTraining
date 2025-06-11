package com.dev.lamariposa.boardgamesassociation.domain.usecase

import com.dev.lamariposa.boardgamesassociation.domain.repository.MyBoardGameRepository

class RemoveBoardGameFromMyGamesUseCase(private val repository: MyBoardGameRepository) {
    suspend operator fun invoke(myBoardGameId: Long) {
        repository.deleteMyBoardGame(myBoardGameId)
    }
}
