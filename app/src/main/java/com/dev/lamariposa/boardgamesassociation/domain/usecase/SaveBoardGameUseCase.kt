package com.dev.lamariposa.boardgamesassociation.domain.usecase

import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGame
import com.dev.lamariposa.boardgamesassociation.domain.repository.BoardGameRepository

class SaveBoardGameUseCase(private val repository: BoardGameRepository) {
    suspend operator fun invoke(boardGame: BoardGame): Long {
        return repository.saveBoardGame(boardGame)
    }
}
