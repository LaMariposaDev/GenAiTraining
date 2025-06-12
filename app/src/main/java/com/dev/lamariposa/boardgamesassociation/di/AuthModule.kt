package com.dev.lamariposa.boardgamesassociation.di

import com.dev.lamariposa.boardgamesassociation.data.api.auth.FirebaseAuthService
import com.dev.lamariposa.boardgamesassociation.data.repository.AuthRepositoryImpl
import com.dev.lamariposa.boardgamesassociation.domain.repository.AuthRepository
import com.dev.lamariposa.boardgamesassociation.domain.usecase.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module

/**
 * Koin module for authentication related dependencies
 */
val authModule = module {
    
    // Firebase Services
    single { 
        FirebaseAuth.getInstance() 
    }
    
    single { 
        FirebaseFirestore.getInstance() 
    }
    
    // Firebase Auth Service
    single { 
        FirebaseAuthService(get(), get()) 
    }
    
    // Auth Repository
    single<AuthRepository> { 
        AuthRepositoryImpl(get()) 
    }
    
    // Individual Use Cases
    factory { 
        GetCurrentUserUseCase(get()) 
    }
    factory { 
        SignInUseCase(get()) 
    }
    factory { 
        CreateUserUseCase(get()) 
    }
    factory { 
        SendPasswordResetEmailUseCase(get()) 
    }
    factory { 
        SendEmailVerificationUseCase(get()) 
    }
    factory { 
        SignOutUseCase(get()) 
    }
    factory { 
        IsEmailVerifiedUseCase(get()) 
    }
    
    // Combined Use Cases
    factory {
        AuthUseCases(get(),get(),get(),get(),get(),get(),get())
    }
}
