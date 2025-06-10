package com.dev.lamariposa.boardgamesassociation.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "board_game")
data class BoardGameEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val year_published: Int?,
    val description: String?,
    val image_url: String?,
    val type: String
)
