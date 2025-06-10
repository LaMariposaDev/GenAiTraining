package com.dev.lamariposa.boardgamesassociation.data.api.mapper

import com.dev.lamariposa.boardgamesassociation.data.api.model.BoardGameDetailsItem
import com.dev.lamariposa.boardgamesassociation.data.api.model.BoardGameSearchItem
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGame

fun BoardGameSearchItem.toDomainModel(): BoardGame {
    return BoardGame(
        id = id?.toIntOrNull() ?: 0,
        name = name?.value ?: "",
        yearPublished = yearPublished?.value?.toIntOrNull(),
        description = null,
        imageUrl = null
    )
}

fun BoardGameDetailsItem.toDomainModel(): BoardGame {
    return BoardGame(
        id = id?.toIntOrNull() ?: 0,
        name = name?.value ?: "",
        yearPublished = yearPublished?.value?.toIntOrNull(),
        description = description,
        imageUrl = image
    )
}
