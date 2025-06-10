package com.dev.lamariposa.boardgamesassociation.domain.model

data class BoardGame(
    val id: Int,
    val name: String,
    val yearPublished: Int? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val type: String = "Board game" // Default to "Board game" if not specified
)
