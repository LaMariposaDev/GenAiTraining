package com.dev.lamariposa.boardgamesassociation.data.db.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.dev.lamariposa.boardgamesassociation.data.db.entities.BoardGameEntity
import com.dev.lamariposa.boardgamesassociation.data.db.entities.MyBoardGameEntity
import com.dev.lamariposa.boardgamesassociation.data.db.entities.PersonEntity

data class MyBoardGameWithDetails(
    @Embedded
    val myBoardGame: MyBoardGameEntity,
    
    @Relation(
        parentColumn = "boardGameId",
        entityColumn = "id"
    )
    val boardGame: BoardGameEntity,
    
    @Relation(
        parentColumn = "ownerId",
        entityColumn = "id"
    )
    val owner: PersonEntity?,
    
    @Relation(
        parentColumn = "holderId",
        entityColumn = "id"
    )
    val holder: PersonEntity?
)
