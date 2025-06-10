package com.dev.lamariposa.boardgamesassociation.di

import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.SearchViewModel
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.MyGamesViewModel
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // General app-wide dependencies
}

val dataModule = module {
    // Data layer dependencies (e.g., DAOs, Retrofit, repositories)
}

val domainModule = module {
    // Domain layer dependencies (e.g., use cases)
}

val presentationModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { MyGamesViewModel() }
    viewModel { DashboardViewModel() }
}
