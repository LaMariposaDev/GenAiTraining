package com.dev.lamariposa.boardgamesassociation.data.api

import com.dev.lamariposa.boardgamesassociation.data.api.model.BoardGameDetailsResponse
import com.dev.lamariposa.boardgamesassociation.data.api.model.BoardGameSearchResponse
import com.dev.lamariposa.boardgamesassociation.data.mock.MockXmlProvider
import org.simpleframework.xml.core.Persister

/**
 * Mock implementation of BoardGameApiService that returns predefined XML responses
 */
class MockBoardGameApiService : BoardGameApiService {
    
    private val serializer = Persister()
    
    override suspend fun searchBoardGames(query: String): BoardGameSearchResponse {
        // Get mock XML data
        val xmlData = MockXmlProvider.getMockSearchResponse()
        
        // Parse the XML string into BoardGameSearchResponse object
        return serializer.read(BoardGameSearchResponse::class.java, xmlData)
    }
    
    override suspend fun getBoardGameDetails(id: String): BoardGameDetailsResponse {
        // Get mock XML data for board game details
        val xmlData = MockXmlProvider.getMockBoardGameDetailsResponse2(id)

        // Parse the XML string into BoardGameDetailsResponse object
        return serializer.read(BoardGameDetailsResponse::class.java, xmlData)
    }
}
