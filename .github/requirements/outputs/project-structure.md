# Project Structure

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
│   │   │   │   │   ├── search/      # Search board games
│   │   │   │   │   ├── mygames/     # "My Games" list
│   │   │   │   │   ├── details/     # Board game details
│   │   │   │   ├── viewmodel/       # ViewModels for each screen
│   │   │   ├── util/                # Utility classes/helpers
│   │   ├── res/
│   │   │   ├── layout/              # XML layouts
│   │   │   ├── drawable/            # Images and icons
│   │   │   ├── values/              # Strings, colors, styles
│   │   ├── AndroidManifest.xml
├── build.gradle
```

## Layer Overview

- **data/**: Handles data sources (API, database), mapping, and repository implementations.
- **domain/**: Contains business logic, models, and repository interfaces.
- **presentation/**: UI, ViewModels, and navigation.
- **di/**: Koin dependency injection setup.
- **util/**: Utility classes and helpers.

## Notes

- Follows Clean Architecture and MVVM.
- Uses Room for local storage and BoardGameGeek XML API for remote data.
- Uses Koin for dependency injection.
- Organized for testability and separation of concerns.
