package com.dev.lamariposa.boardgamesassociation.data.api

import com.dev.lamariposa.boardgamesassociation.data.api.model.BoardGameDetailsResponse
import com.dev.lamariposa.boardgamesassociation.data.api.model.BoardGameSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BoardGameGeekApi {
    // Search board games by name
    // GET https://boardgamegeek.com/xmlapi2/search?query={name}&type=boardgame
    @GET("search")
    suspend fun searchBoardGames(
        @Query("query") query: String,
        @Query("type") type: String = "boardgame"
    ): BoardGameSearchResponse

    // Get board game details by id
    // GET https://boardgamegeek.com/xmlapi2/thing?id={id}
    @GET("thing")
    suspend fun getBoardGameDetails(
        @Query("id") id: String
    ): BoardGameDetailsResponse
}
