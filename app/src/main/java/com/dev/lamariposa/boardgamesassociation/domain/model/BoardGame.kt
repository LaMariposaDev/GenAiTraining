package com.dev.lamariposa.boardgamesassociation.domain.model

data class BoardGame(
    val id: Int,
    val name: String,
    val yearPublished: Int?,
    val description: String?,
    val imageUrl: String?
)
