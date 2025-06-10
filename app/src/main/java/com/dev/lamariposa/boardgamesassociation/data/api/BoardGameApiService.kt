package com.dev.lamariposa.boardgamesassociation.data.api

import com.dev.lamariposa.boardgamesassociation.data.api.model.BoardGameDetailsResponse
import com.dev.lamariposa.boardgamesassociation.data.api.model.BoardGameSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BoardGameApiService {
    @GET("xmlapi2/search")
    suspend fun searchBoardGames(@Query("query") query: String): BoardGameSearchResponse
    
    @GET("xmlapi2/thing")
    suspend fun getBoardGameDetails(@Query("id") id: String): BoardGameDetailsResponse
}
