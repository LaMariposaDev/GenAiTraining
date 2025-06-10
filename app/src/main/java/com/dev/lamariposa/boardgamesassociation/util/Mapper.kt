package com.dev.lamariposa.boardgamesassociation.util

import com.dev.lamariposa.boardgamesassociation.data.db.entities.BoardGameEntity
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGame

object Mapper {
    fun entityToDomain(entity: BoardGameEntity): BoardGame =
        BoardGame(
            id = entity.id,
            name = entity.name,
            yearPublished = entity.year_published,
            description = entity.description,
            imageUrl = entity.image_url
        )
}
