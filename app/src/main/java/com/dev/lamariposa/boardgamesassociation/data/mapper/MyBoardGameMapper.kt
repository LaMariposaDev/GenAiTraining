package com.dev.lamariposa.boardgamesassociation.data.mapper

import com.dev.lamariposa.boardgamesassociation.data.db.entities.MyBoardGameEntity
import com.dev.lamariposa.boardgamesassociation.data.db.relations.MyBoardGameWithDetails
import com.dev.lamariposa.boardgamesassociation.domain.model.MyBoardGame
import java.util.Date

fun MyBoardGameWithDetails.toDomain(): MyBoardGame {
    return MyBoardGame(
        id = myBoardGame.id,
        boardGame = boardGame.toDomain(),
        owner = owner?.toDomain(),
        holder = holder?.toDomain(),
        dateAdded = Date(myBoardGame.dateAdded),
        notes = myBoardGame.notes
    )
}

fun MyBoardGame.toEntity(): MyBoardGameEntity {
    return MyBoardGameEntity(
        id = id,
        boardGameId = boardGame.id,
        ownerId = owner?.id,
        holderId = holder?.id,
        dateAdded = dateAdded.time,
        notes = notes
    )
}
