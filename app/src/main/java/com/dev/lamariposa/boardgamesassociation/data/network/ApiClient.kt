package com.dev.lamariposa.boardgamesassociation.data.network

import com.dev.lamariposa.boardgamesassociation.data.api.BoardGameGeekApi
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object ApiClient {
    private const val BASE_URL = "https://boardgamegeek.com/xmlapi2/"

    val api: BoardGameGeekApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(BoardGameGeekApi::class.java)
    }
}
