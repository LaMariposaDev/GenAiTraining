package com.dev.lamariposa.boardgamesassociation.domain.model

/**
 * Detailed representation of a board game
 */
data class BoardGameDetail(
    val id: Int,
    val name: String,
    val yearPublished: Int? = null,
    val description: String = "",
    val imageUrl: String? = null,
    val thumbnailUrl: String? = null,
    val minPlayers: Int = 0,
    val maxPlayers: Int = 0,
    val playingTime: Int = 0,
    val type: String = "Board game" // Default to "Board game" if not specified
)
