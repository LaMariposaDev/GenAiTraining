package com.dev.lamariposa.boardgamesassociation.domain.usecase

import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGame
import com.dev.lamariposa.boardgamesassociation.domain.model.MyBoardGame
import com.dev.lamariposa.boardgamesassociation.domain.model.Person
import com.dev.lamariposa.boardgamesassociation.domain.repository.BoardGameRepository
import com.dev.lamariposa.boardgamesassociation.domain.repository.MyBoardGameRepository

class AddBoardGameToMyGamesUseCase(
    private val myBoardGameRepository: MyBoardGameRepository,
    private val boardGameRepository: BoardGameRepository
) {
    suspend operator fun invoke(
        boardGame: BoardGame,
        owner: Person? = null,
        holder: Person? = null,
        notes: String? = null
    ): Long {
        // First, ensure the board game is saved to the database
        boardGameRepository.saveBoardGame(boardGame)
        
        // Check if the board game is already in "My Games"
        val existingMyBoardGame = boardGameRepository.getBoardGameById(boardGame.id)?.let { savedBoardGame ->
            myBoardGameRepository.getMyBoardGameByBoardGameId(savedBoardGame.id)
        }
        
        return if (existingMyBoardGame != null) {
            // Update the existing entry
            val updatedMyBoardGame = existingMyBoardGame.copy(
                owner = owner ?: existingMyBoardGame.owner,
                holder = holder ?: existingMyBoardGame.holder,
                notes = notes ?: existingMyBoardGame.notes
            )
            myBoardGameRepository.updateMyBoardGame(updatedMyBoardGame)
            existingMyBoardGame.id
        } else {
            // Create a new entry
            val myBoardGame = MyBoardGame(
                boardGame = boardGame,
                owner = owner,
                holder = holder,
                notes = notes
            )
            myBoardGameRepository.addBoardGameToCollection(myBoardGame)
        }
    }
}
