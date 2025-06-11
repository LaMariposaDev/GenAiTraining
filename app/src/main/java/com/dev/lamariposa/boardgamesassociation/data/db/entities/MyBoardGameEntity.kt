package com.dev.lamariposa.boardgamesassociation.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "my_board_game",
    foreignKeys = [
        ForeignKey(
            entity = BoardGameEntity::class,
            parentColumns = ["id"],
            childColumns = ["boardGameId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PersonEntity::class,
            parentColumns = ["id"],
            childColumns = ["ownerId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = PersonEntity::class,
            parentColumns = ["id"],
            childColumns = ["holderId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index("boardGameId"),
        Index("ownerId"),
        Index("holderId")
    ]
)
data class MyBoardGameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val boardGameId: Int,
    val ownerId: Long?,
    val holderId: Long?,
    val dateAdded: Long,
    val notes: String?
)
