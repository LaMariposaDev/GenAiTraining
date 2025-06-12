package com.dev.lamariposa.boardgamesassociation.di

import android.util.Log
import com.dev.lamariposa.boardgamesassociation.data.api.BoardGameApiService
import com.dev.lamariposa.boardgamesassociation.data.db.AppDatabase
import com.dev.lamariposa.boardgamesassociation.data.db.BoardGameDao
import com.dev.lamariposa.boardgamesassociation.data.db.MyBoardGameDao
import com.dev.lamariposa.boardgamesassociation.data.db.PersonDao
import com.dev.lamariposa.boardgamesassociation.data.repository.BoardGameRepositoryImpl
import com.dev.lamariposa.boardgamesassociation.data.repository.MyBoardGameRepositoryImpl
import com.dev.lamariposa.boardgamesassociation.data.repository.PersonRepositoryImpl
import com.dev.lamariposa.boardgamesassociation.domain.repository.BoardGameRepository
import com.dev.lamariposa.boardgamesassociation.domain.repository.MyBoardGameRepository
import com.dev.lamariposa.boardgamesassociation.domain.repository.PersonRepository
import com.dev.lamariposa.boardgamesassociation.domain.usecase.AddBoardGameToMyGamesUseCase
import com.dev.lamariposa.boardgamesassociation.domain.usecase.AddPersonUseCase
import com.dev.lamariposa.boardgamesassociation.domain.usecase.GetBoardGameDetailsUseCase
import com.dev.lamariposa.boardgamesassociation.domain.usecase.GetMyBoardGamesUseCase
import com.dev.lamariposa.boardgamesassociation.domain.usecase.GetPersonsUseCase
import com.dev.lamariposa.boardgamesassociation.domain.usecase.RemoveBoardGameFromMyGamesUseCase
import com.dev.lamariposa.boardgamesassociation.domain.usecase.SaveBoardGameUseCase
import com.dev.lamariposa.boardgamesassociation.domain.usecase.SearchBoardGamesUseCase
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.AuthViewModel
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.BoardGameDetailViewModel
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
import com.dev.lamariposa.boardgamesassociation.BuildConfig

val appModule = module {
    // General app-wide dependencies
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }

    // Database
    single { AppDatabase(get()) }
    single { provideBoardGameDao(get()) }
    single { providePersonDao(get()) }
    single { provideMyBoardGameDao(get()) }

    single { provideBoardGameApiService(get()) }
}

val dataModule = module {
    // Data layer dependencies
    single<BoardGameRepository> { BoardGameRepositoryImpl(get(), get()) }
    single<PersonRepository> { PersonRepositoryImpl(get()) }
    single<MyBoardGameRepository> { MyBoardGameRepositoryImpl(get()) }
}

val domainModule = module {
    // Domain layer dependencies (use cases)
    factory { SearchBoardGamesUseCase(get()) }
    factory { GetBoardGameDetailsUseCase(get()) }
    factory { AddBoardGameToMyGamesUseCase(get(), get()) }
    factory { GetMyBoardGamesUseCase(get()) }
    factory { RemoveBoardGameFromMyGamesUseCase(get()) }
    factory { AddPersonUseCase(get()) }
    factory { GetPersonsUseCase(get()) }
    factory { SaveBoardGameUseCase(get()) }
}

val presentationModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { MyGamesViewModel(get(), get()) }
    viewModel { DashboardViewModel() }
    viewModel { BoardGameDetailViewModel(get(), get(), get(), get()) }
    viewModel { AuthViewModel(get()) }
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
    Log.d("provideRetrofit", "Creating Retrofit instance with base URL: https://boardgamegeek.com/ ${BuildConfig.BGG_API_TOKEN}")
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(
            client.newBuilder().addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.BGG_API_TOKEN}")
                    .build()

                chain.proceed(request)
            }.build()
        )
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()
}

private fun provideBoardGameApiService(retrofit: Retrofit): BoardGameApiService {
    return retrofit.create(BoardGameApiService::class.java)
}

private fun provideBoardGameDao(database: AppDatabase): BoardGameDao = database.boardGameDao()
private fun providePersonDao(database: AppDatabase): PersonDao = database.personDao()
private fun provideMyBoardGameDao(database: AppDatabase): MyBoardGameDao = database.myBoardGameDao()
