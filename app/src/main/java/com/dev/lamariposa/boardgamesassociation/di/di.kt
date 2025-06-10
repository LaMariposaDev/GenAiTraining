package com.dev.lamariposa.boardgamesassociation.di

import com.dev.lamariposa.boardgamesassociation.data.api.BoardGameApiService
import com.dev.lamariposa.boardgamesassociation.data.api.MockBoardGameApiService
import com.dev.lamariposa.boardgamesassociation.data.db.BoardGameDao
import com.dev.lamariposa.boardgamesassociation.data.repository.BoardGameRepositoryImpl
import com.dev.lamariposa.boardgamesassociation.domain.repository.BoardGameRepository
import com.dev.lamariposa.boardgamesassociation.domain.usecase.SearchBoardGamesUseCase
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.DashboardViewModel
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.MyGamesViewModel
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.SearchViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    // General app-wide dependencies
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    
    // Use mock implementation instead of real API service
    single<BoardGameApiService> { MockBoardGameApiService() }
}

val dataModule = module {
    // Data layer dependencies
    single<BoardGameRepository> { BoardGameRepositoryImpl(get(), get()) }
    
    // Temporarily providing a mock DAO implementation
    single { provideMockBoardGameDao() }
}

val domainModule = module {
    // Domain layer dependencies (use cases)
    factory { SearchBoardGamesUseCase(get()) }
}

val presentationModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { MyGamesViewModel() }
    viewModel { DashboardViewModel() }
}

// Network providers
private fun provideOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
}

private fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://boardgamegeek.com/")
        .client(client)
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()
}

private fun provideBoardGameApiService(retrofit: Retrofit): BoardGameApiService {
    return retrofit.create(BoardGameApiService::class.java)
}

// Temporary mock DAO until we implement the database
private fun provideMockBoardGameDao(): BoardGameDao {
    return object : BoardGameDao {
        override suspend fun insertBoardGame(game: com.dev.lamariposa.boardgamesassociation.data.db.entities.BoardGameEntity) {
            // No-op for now
        }

        override suspend fun searchBoardGames(query: String): List<com.dev.lamariposa.boardgamesassociation.data.db.entities.BoardGameEntity> {
            return emptyList()
        }
    }
}
