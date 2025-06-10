package com.dev.lamariposa.boardgamesassociation.domain.usecase

import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGame
import com.dev.lamariposa.boardgamesassociation.domain.repository.BoardGameRepository

class SearchBoardGamesUseCase(private val repository: BoardGameRepository) {
    suspend operator fun invoke(query: String): List<BoardGame> {
        return repository.searchBoardGames(query)
    }
}
