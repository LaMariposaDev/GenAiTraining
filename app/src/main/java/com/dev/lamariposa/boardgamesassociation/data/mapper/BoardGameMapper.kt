package com.dev.lamariposa.boardgamesassociation.data.mapper

import com.dev.lamariposa.boardgamesassociation.data.db.entities.BoardGameEntity
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGame

fun BoardGameEntity.toDomain(): BoardGame {
    return BoardGame(
        id = id,
        name = name,
        yearPublished = year_published,
        description = description,
        imageUrl = image_url,
        type = type
    )
}

fun BoardGame.toEntity(): BoardGameEntity {
    return BoardGameEntity(
        id = id,
        name = name,
        year_published = yearPublished,
        description = description,
        image_url = imageUrl,
        type = type
    )
}
