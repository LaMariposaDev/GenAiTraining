package com.dev.lamariposa.boardgamesassociation.domain.model

import java.util.Date

data class MyBoardGame(
    val id: Long = 0,
    val boardGame: BoardGame,
    val owner: Person? = null,
    val holder: Person? = null,
    val dateAdded: Date = Date(),
    val notes: String? = null
)
