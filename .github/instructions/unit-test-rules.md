# Unit Testing Rules for Board Games App

## Project Structure

Unit tests should be organized in the `app/src/test/` directory, mirroring the package structure of the main source code. For example:

```
app/
├── src/
│   ├── test/
│   │   ├── java/com/example/boardgames/
│   │   │   ├── data/
│   │   │   │   ├── repository/       # Unit tests for repositories
│   │   │   │   ├── api/              # Unit tests for API services
│   │   │   ├── domain/
│   │   │   │   ├── usecase/          # Unit tests for use cases
│   │   │   ├── presentation/
│   │   │   │   ├── viewmodel/        # Unit tests for ViewModels
```

## Test Dependencies

Add the following dependencies to your `build.gradle` file:

```gradle
dependencies {
    // Required -- JUnit 4 framework
    testImplementation "junit:junit:$jUnitVersion"
    
    // Optional -- Mockito core
    testImplementation "org.mockito:mockito-core:$mockitoVersion"
    
    // Optional -- mockito-kotlin
    testImplementation "org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion"
    
    // Optional -- Mockk framework (Kotlin-friendly mocking)
    testImplementation "io.mockk:mockk:$mockkVersion"
    
    // Optional -- Coroutines test
    testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion"
    
    // Optional -- Robolectric for Android dependencies
    testImplementation "androidx.test:core:$androidXTestVersion"
    testImplementation "org.robolectric:robolectric:$robolectricVersion"
}
```

## General Unit Testing Rules

1. **Test One Thing Per Test**: Each test method should verify a single aspect of functionality.

2. **Follow AAA Pattern**: Structure tests using Arrange-Act-Assert:
   - **Arrange**: Set up test prerequisites
   - **Act**: Execute the code being tested
   - **Assert**: Verify expected outcomes

3. **Descriptive Test Names**: Use clear, descriptive method names that explain what is being tested.
   - Format: `methodName_testCondition_expectedResult`
   - Example: `calculateTotal_withNegativeQuantity_throwsException`

4. **Independent Tests**: Tests should not depend on other tests or external state.

5. **Fast Execution**: Unit tests should run quickly (milliseconds per test).

6. **Avoid Static State**: Don't use static variables that could cause tests to interfere with each other.


## What to Test

### 1. ViewModels (Priority)
- Test all public methods and LiveData/Flow emissions
- Verify correct interaction with use cases
- Test error handling and loading states
- Test data transformations

### 2. Use Cases (Priority)
- Test business logic thoroughly
- Test edge cases and error conditions
- Verify correct interactions with repositories

### 3. Repositories (Priority)
- Test source selection logic (API vs local database)
- Test data mapping between layers
- Test caching mechanisms
- Test error handling

### 4. Utility Classes
- Test helper functions thoroughly
- Test string manipulations
- Test mathematical operations
- Test date/time conversions

### 5. Data Mappers
- Test mapping between domain models and data models
- Test handling of null or empty values

## What NOT to Test

1. Generated code (e.g., Room DAOs)
2. Simple pass-through methods
3. Framework code (Android, Koin, etc.)
4. UI components (Activities, Fragments) - use instrumented tests instead
5. Private methods directly (test through public methods)

## Mocking Guidelines

1. **Use Mockito-Kotlin or MockK**: Prefer Kotlin-friendly mocking libraries.

2. **Avoid Complex Mocks**: Don't create complex mock behaviors. Use fakes or simple mocks instead.

3. **Mock External Dependencies**: Mock repositories in ViewModel tests, data sources in repository tests.

4. **Mock Android Dependencies**: Use mocks for Context, Resources, and other Android classes.

```kotlin
// Example of mocking with Mockito-Kotlin
@RunWith(MockitoJUnitRunner::class)
class GameRepositoryTest {
    @Mock
    private lateinit var apiService: BoardGameApiService
    
    @Mock
    private lateinit var gameDao: GameDao
    
    private lateinit var repository: GameRepositoryImpl
    
    @Before
    fun setup() {
        repository = GameRepositoryImpl(apiService, gameDao)
    }
    
    @Test
    fun getGame_apiSuccess_returnsGame() = runTest {
        // Arrange
        val gameId = "123"
        val apiGame = GameDto(id = gameId, name = "Catan")
        val expectedGame = Game(id = gameId, name = "Catan")
        
        whenever(apiService.getGame(gameId)).thenReturn(apiGame)
        
        // Act
        val result = repository.getGame(gameId)
        
        // Assert
        assertEquals(expectedGame, result)
        verify(gameDao).insertGame(any())
    }
}
```

## Testing Asynchronous Code

1. **Coroutines**: Use `runTest` from `kotlinx-coroutines-test` to test suspending functions.

2. **Flow**: Use `flow.first()` or collect into a list to test Flow emissions.

3. **LiveData**: Use `LiveDataTestUtil` or similar to test LiveData observations.

```kotlin
// Example of testing coroutines
@Test
fun getGames_emitsGamesFromRepository() = runTest {
    // Arrange
    val games = listOf(Game(id = "1", name = "Catan"), Game(id = "2", name = "Azul"))
    coEvery { gameRepository.getGames() } returns flow { emit(games) }
    
    // Act
    val result = getAllGamesUseCase().first()
    
    // Assert
    assertEquals(games, result)
}
```

## Testing Edge Cases

Always test these edge cases:

1. Empty collections
2. Null values
3. Invalid input values
4. Zero, negative, and boundary values for numeric operations
5. Network errors and timeouts
6. Database errors

## Robolectric Guidelines

When Android dependencies cannot be avoided in unit tests:

1. Use Robolectric sparingly and only when necessary
2. Configure Robolectric to use the appropriate SDK version
3. Enable resource processing for tests that require Android resources

```kotlin
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class AndroidDependentTest {
    // Tests that require Android context or resources
}
```

Remember that good unit tests are:
- Fast
- Isolated
- Repeatable
- Self-verifying
- Timely
