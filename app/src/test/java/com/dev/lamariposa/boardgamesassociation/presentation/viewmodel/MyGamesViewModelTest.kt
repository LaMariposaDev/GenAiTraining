package com.dev.lamariposa.boardgamesassociation.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGame
import com.dev.lamariposa.boardgamesassociation.domain.model.MyBoardGame
import com.dev.lamariposa.boardgamesassociation.domain.usecase.GetMyBoardGamesUseCase
import com.dev.lamariposa.boardgamesassociation.domain.usecase.RemoveBoardGameFromMyGamesUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date

@ExperimentalCoroutinesApi
class MyGamesViewModelTest {

    // Rule for LiveData testing
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Test dispatcher for coroutines
    private val testDispatcher = StandardTestDispatcher()

    // Mocks
    @MockK
    private lateinit var getMyBoardGamesUseCase: GetMyBoardGamesUseCase

    @MockK
    private lateinit var removeBoardGameFromMyGamesUseCase: RemoveBoardGameFromMyGamesUseCase

    // Error observer for LiveData
    @MockK
    private lateinit var errorObserver: Observer<String>

    // ViewModel instance under test
    private lateinit var viewModel: MyGamesViewModel

    // Sample data for testing
    private val testBoardGame1 = BoardGame(
        id = 1,
        name = "Catan",
        yearPublished = 1995,
        description = "Settlers of Catan board game",
        imageUrl = "https://example.com/catan.jpg",
        type = "Board game"
    )

    private val testBoardGame2 = BoardGame(
        id = 2,
        name = "Ticket to Ride",
        yearPublished = 2004,
        description = "Railway-themed board game",
        imageUrl = "https://example.com/ticket-to-ride.jpg",
        type = "Board game"
    )

    private val testMyBoardGames = listOf(
        MyBoardGame(
            id = 1L,
            boardGame = testBoardGame1,
            dateAdded = Date()
        ),
        MyBoardGame(
            id = 2L,
            boardGame = testBoardGame2,
            dateAdded = Date()
        )
    )

    @Before
    fun setUp() {
        // Initialize MockK
        MockKAnnotations.init(this)
        
        // Set up the test dispatcher
        Dispatchers.setMain(testDispatcher)
        
        // Set up default stubs
        every { getMyBoardGamesUseCase.invoke() } returns flowOf(testMyBoardGames)
        coEvery { removeBoardGameFromMyGamesUseCase.invoke(any()) } just runs
        
        // Set up ViewModel
        viewModel = MyGamesViewModel(
            getMyBoardGamesUseCase,
            removeBoardGameFromMyGamesUseCase
        )
        
        // Set up the error observer
        viewModel.error.observeForever(errorObserver)
    }

    @After
    fun tearDown() {
        // Clear mocks
        clearAllMocks()
        
        // Remove error observer
        viewModel.error.removeObserver(errorObserver)
        
        // Reset the main dispatcher
        Dispatchers.resetMain()
    }

    @Test
    fun loadMyBoardGames_success_updatesStateWithGames() = runTest {
        // Arrange
        
        // Act - loadMyBoardGames is called in init block of ViewModel
        testScheduler.advanceUntilIdle()
        
        // Assert
        assertEquals(testMyBoardGames, viewModel.myBoardGames.value)
        assertEquals(false, viewModel.loading.value)
        verify { getMyBoardGamesUseCase.invoke() }
        verify(exactly = 0) { errorObserver.onChanged(any()) }
    }

    @Test
    fun loadMyBoardGames_error_updatesErrorState() = runTest {
        // Arrange
        val errorMessage = "Network error"
        every { getMyBoardGamesUseCase.invoke() } returns flow {
            throw Exception(errorMessage)
        }
        every { errorObserver.onChanged(any()) } just Runs

        // Create a new ViewModel instance to trigger init block with our mocked error
        val errorViewModel = MyGamesViewModel(
            getMyBoardGamesUseCase,
            removeBoardGameFromMyGamesUseCase
        )
        errorViewModel.error.observeForever(errorObserver)
        
        // Act
        testScheduler.advanceUntilIdle()
        
        // Assert
        verify { errorObserver.onChanged(match { it.contains(errorMessage) }) }
        assertEquals(false, errorViewModel.loading.value)
        
        // Cleanup
        errorViewModel.error.removeObserver(errorObserver)
    }

    @Test
    fun removeBoardGameFromCollection_success_refreshesList() = runTest {
        // Arrange
        val boardGameIdToRemove = 1L
        coEvery { removeBoardGameFromMyGamesUseCase.invoke(boardGameIdToRemove) } just runs
        
        // Reset invocation counts for getMyBoardGamesUseCase
        clearMocks(getMyBoardGamesUseCase)
        every { getMyBoardGamesUseCase.invoke() } returns flowOf(testMyBoardGames)
        
        // Act
        viewModel.removeBoardGameFromCollection(boardGameIdToRemove)
        testScheduler.advanceUntilIdle()
        
        // Assert
        coVerify(exactly = 1) { removeBoardGameFromMyGamesUseCase.invoke(boardGameIdToRemove) }
        verify(exactly = 1) { getMyBoardGamesUseCase.invoke() } // Verify list is refreshed
        verify(exactly = 0) { errorObserver.onChanged(any()) }
    }

    @Test
    fun removeBoardGameFromCollection_error_updatesErrorState() = runTest {
        // Arrange
        val boardGameIdToRemove = 1L
        val errorMessage = "Database error"
        coEvery { removeBoardGameFromMyGamesUseCase.invoke(boardGameIdToRemove) } throws Exception(errorMessage)
        every { errorObserver.onChanged(any()) } just Runs

        // Act
        viewModel.removeBoardGameFromCollection(boardGameIdToRemove)
        testScheduler.advanceUntilIdle()
        
        // Assert
        coVerify(exactly = 1) { removeBoardGameFromMyGamesUseCase.invoke(boardGameIdToRemove) }
        verify { errorObserver.onChanged(match { it.contains(errorMessage) }) }
    }

    @Test
    fun loadingState_isUpdatedCorrectly() = runTest {
        // Arrange
        
        // Setup a delayed flow to test loading state transitions
        val delayedFlow = flow<List<MyBoardGame>> {
            emit(testMyBoardGames)
        }
        every { getMyBoardGamesUseCase.invoke() } returns delayedFlow
        
        // Create a new ViewModel to trigger loading
        val loadingViewModel = MyGamesViewModel(
            getMyBoardGamesUseCase,
            removeBoardGameFromMyGamesUseCase
        )

        // Act & Assert - loading becomes true immediately
        assertEquals(true, loadingViewModel.loading.value)
        
        // Advance coroutines to complete the flow
        testScheduler.advanceUntilIdle()
        
        // Assert - loading becomes false after completion
        assertEquals(false, loadingViewModel.loading.value)
    }
}