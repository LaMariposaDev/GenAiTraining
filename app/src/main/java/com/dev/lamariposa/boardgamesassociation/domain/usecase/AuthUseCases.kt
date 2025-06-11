package com.dev.lamariposa.boardgamesassociation.domain.usecase

/**
 * Combined auth use cases for easier injection
 */
data class AuthUseCases(
    val getCurrentUser: GetCurrentUserUseCase,
    val signIn: SignInUseCase,
    val createUser: CreateUserUseCase,
    val sendPasswordResetEmail: SendPasswordResetEmailUseCase,
    val sendEmailVerification: SendEmailVerificationUseCase,
    val signOut: SignOutUseCase,
    val isEmailVerified: IsEmailVerifiedUseCase
)
