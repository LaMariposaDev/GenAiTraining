package com.dev.lamariposa.boardgamesassociation.data.api.mapper

import com.dev.lamariposa.boardgamesassociation.data.api.model.BoardGameDetailsItem
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGame
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGameDetail

/**
 * Function to convert BoardGameDetailsItem to BoardGameDetail domain model
 */
fun toDomainModel(item: BoardGameDetailsItem): BoardGameDetail {
    val gameType = when (item.type) {
        "boardgameaccessory" -> "Accessory"
        "boardgameexpansion" -> "Extension"
        else -> "Board game"
    }
    
    return BoardGameDetail(
        id = item.id?.toIntOrNull() ?: 0,
        name = item.primaryName ?: "",
        yearPublished = item.yearPublished?.value?.toIntOrNull(),
        description = item.description ?: "",
        imageUrl = item.image,
        thumbnailUrl = item.thumbnail,
        minPlayers = item.minPlayers?.value?.toIntOrNull() ?: 0,
        maxPlayers = item.maxPlayers?.value?.toIntOrNull() ?: 0,
        playingTime = item.playingTime?.value?.toIntOrNull() ?: 0,
        type = gameType
    )
}
