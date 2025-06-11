package com.dev.lamariposa.boardgamesassociation.di

import com.dev.lamariposa.boardgamesassociation.data.api.auth.FirebaseAuthService
import com.dev.lamariposa.boardgamesassociation.data.repository.AuthRepositoryImpl
import com.dev.lamariposa.boardgamesassociation.domain.repository.AuthRepository
import com.dev.lamariposa.boardgamesassociation.domain.usecase.*
import org.koin.dsl.module

/**
 * Koin module for authentication related dependencies
 */
val authModule = module {
    
    // Firebase Auth Service
    single { 
        FirebaseAuthService() 
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
        AuthUseCases(
            getCurrentUser = get(),
            signIn = get(),
            createUser = get(),
            sendPasswordResetEmail = get(),
            sendEmailVerification = get(),
            signOut = get(),
            isEmailVerified = get()
        )
    }
}
