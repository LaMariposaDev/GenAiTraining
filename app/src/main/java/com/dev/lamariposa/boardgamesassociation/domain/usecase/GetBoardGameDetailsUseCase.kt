package com.dev.lamariposa.boardgamesassociation.domain.usecase

import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGameDetail
import com.dev.lamariposa.boardgamesassociation.domain.repository.BoardGameRepository

class GetBoardGameDetailsUseCase(private val repository: BoardGameRepository) {
    suspend operator fun invoke(id: Int): Result<BoardGameDetail> {
        return repository.getBoardGameDetails(id)
    }
}
