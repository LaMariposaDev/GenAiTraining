# Board Games Association App

A mobile app for managing board games, utilizing the BoardGameGeek XML REST API for fetching board game data, and Firebase for user authentication, analytics, and cloud storage.

## Features

- User authentication (email/password)
- Board game search
- Personal collection management
- Detailed board game information
- Offline access to your collection

## Architecture & Technologies

This project follows Clean Architecture principles and uses the MVVM pattern with the following technologies:

- **Kotlin**: Primary programming language
- **XML**: UI layouts
- **Room**: Local data storage
- **Koin**: Dependency injection
- **BoardGameGeek XML REST API**: Board game data
- **Firebase**: Authentication, analytics, and cloud storage
- **Retrofit**: Network requests
- **Coroutines and Flow**: Asynchronous programming
- **Navigation Component**: App navigation
- **Material Design**: UI components

## Setup

### Firebase Setup

The app uses Firebase for authentication, analytics, and cloud storage. To set up Firebase for this project:

1. Create a Firebase project at [firebase.google.com](https://firebase.google.com)
2. Add an Android app to your Firebase project with package name `com.dev.lamariposa.boardgamesassociation`
3. Download the `google-services.json` file and place it in the `/app` directory
4. Enable the Authentication service in your Firebase project
   - Go to Firebase console > Authentication > Sign-in method
   - Enable Email/Password provider
5. (Optional) Set up Firebase Analytics and Crashlytics as needed

### Building the Project

1. Clone the repository
2. Open the project in Android Studio
3. Sync the project with Gradle files
4. Build and run the app

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/boardgames/
│   │   │   ├── di/                  # Koin dependency injection modules
│   │   │   ├── data/                # Data layer
│   │   │   │   ├── api/             # BoardGameGeek XML REST API
│   │   │   │   ├── db/              # Room database entities & DAOs
│   │   │   │   ├── repository/      # Repository implementations
│   │   │   ├── domain/              # Domain layer
│   │   │   │   ├── model/           # Business models/entities
│   │   │   │   ├── repository/      # Repository interfaces
│   │   │   │   ├── usecase/         # Use cases (business logic)
│   │   │   ├── presentation/        # Presentation layer (MVVM)
│   │   │   │   ├── ui/
│   │   │   │   │   ├── main/        # Main activity & navigation
│   │   │   │   │   ├── auth/        # Auth activity & authentication fragments
│   │   │   │   │   ├── search/      # Search board games
│   │   │   │   │   ├── mygames/     # "My Games" list
│   │   │   │   │   ├── details/     # Board game details
│   │   │   │   ├── viewmodel/       # ViewModels for each screen
│   │   │   ├── util/                # Utility classes/helpers
```

## Authentication Flow

The authentication module has been moved to a dedicated `AuthActivity` (`presentation/ui/auth/AuthActivity.kt`).
All authentication-related fragments (login, register, password reset, email verification) are now hosted in this activity, using a separate navigation graph (`auth_navigation.xml`).

### Flow
1. Login with email and password
2. User registration
3. Email verification
4. Password reset
5. Remember me functionality
6. Secure logout


**Note:** The main app flow is now separated from the authentication flow for better modularity and maintainability.

## Auth Architecture Update (2025-06-12)

The authentication flow has been consolidated and simplified:

1. **AuthActivity** is now the single source of truth for authentication state
   - It handles ALL navigation based on authentication state:
     - To MainActivity when authenticated
     - To EmailVerificationFragment when email is unverified
     - To LoginFragment when unauthenticated
   - It observes `AuthViewModel.authState` and responds to state changes
   - It uses the `FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK` flags when starting MainActivity to prevent navigation back to login

2. **Auth Fragments** have been streamlined
   - They focus on their specific responsibilities (login, register, password reset, email verification)
   - They no longer observe or handle auth state at all (this is now exclusive to AuthActivity)
   - They only observe loading states and error messages
   - They use consistent error handling, loading states, and UI patterns

3. **AuthViewModel** remains unchanged, but is now used more consistently
   - It provides a single source of truth for authentication state
   - It handles all authentication operations (sign in, register, password reset, etc.)
   - It provides loading states and error handling

## Auth Navigation Update (2025-06-12)

After successful login or registration, the user is now automatically navigated from `AuthActivity` to `MainActivity`, and `AuthActivity` is finished. This ensures the authentication flow is properly separated from the main app navigation. If the user is not authenticated, they remain in `AuthActivity`.

**Implementation:**
- The `AuthActivity` observes `authViewModel.authState`. When the state becomes `AUTHENTICATED`, it starts `MainActivity` and calls `finish()`.
- Logging statements have been added for easier debugging and tracking of navigation events.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request
