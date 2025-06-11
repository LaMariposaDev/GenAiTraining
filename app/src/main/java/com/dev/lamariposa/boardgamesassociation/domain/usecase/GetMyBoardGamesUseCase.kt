package com.dev.lamariposa.boardgamesassociation.domain.usecase

import com.dev.lamariposa.boardgamesassociation.domain.repository.MyBoardGameRepository

class GetMyBoardGamesUseCase(private val repository: MyBoardGameRepository) {
    operator fun invoke() = repository.getAllMyBoardGames()
}
