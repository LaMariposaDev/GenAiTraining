package com.dev.lamariposa.boardgamesassociation.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGame
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGameDetail
import com.dev.lamariposa.boardgamesassociation.domain.model.Person
import com.dev.lamariposa.boardgamesassociation.domain.usecase.AddBoardGameToMyGamesUseCase
import com.dev.lamariposa.boardgamesassociation.domain.usecase.GetBoardGameDetailsUseCase
import com.dev.lamariposa.boardgamesassociation.domain.usecase.GetCurrentUserUseCase
import com.dev.lamariposa.boardgamesassociation.domain.usecase.GetPersonsUseCase
import com.dev.lamariposa.boardgamesassociation.domain.usecase.GetUsersFromFirebaseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BoardGameDetailViewModel(
    private val getBoardGameDetailsUseCase: GetBoardGameDetailsUseCase,
    private val addBoardGameToMyGamesUseCase: AddBoardGameToMyGamesUseCase,
    private val getPersonsUseCase: GetPersonsUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUsersFromFirebaseUseCase: GetUsersFromFirebaseUseCase
) : ViewModel() {
    
    private val _uiState = MutableLiveData<BoardGameDetailUiState>(BoardGameDetailUiState.Loading)
    val uiState: LiveData<BoardGameDetailUiState> = _uiState
    
    private val _persons = MutableStateFlow<List<Person>>(emptyList())
    val persons: StateFlow<List<Person>> = _persons.asStateFlow()
    
    private val _addToCollectionStatus = MutableStateFlow<AddToCollectionStatus>(AddToCollectionStatus.Idle)
    val addToCollectionStatus: StateFlow<AddToCollectionStatus> = _addToCollectionStatus.asStateFlow()
    
    private val _currentUserPerson = MutableStateFlow<Person?>(null)
    val currentUserPerson: StateFlow<Person?> = _currentUserPerson.asStateFlow()
    
    init {
        loadCurrentUser()
        loadUsers()
    }
    
    private fun loadCurrentUser() {
        viewModelScope.launch {
            try {
                val currentUser = getCurrentUserUseCase()
                currentUser?.email?.let { email ->
                    val persons = getPersonsUseCase()
                    _currentUserPerson.value = persons.find { it.email == email }
                    
                    // If no person with the current user's email exists, we'll rely on loadUsers to create one
                    if (_currentUserPerson.value == null) {
                        Log.d("BoardGameDetailVM", "Current user not found as Person, will be created in loadUsers")
                    }
                }
            } catch (e: Exception) {
                Log.e("BoardGameDetailVM", "Error loading current user", e)
            }
        }
    }
    
    fun loadBoardGameDetails(id: Int) {
        viewModelScope.launch {
            _uiState.value = BoardGameDetailUiState.Loading
            
            getBoardGameDetailsUseCase(id).fold(
                onSuccess = { boardGameDetail ->
                    _uiState.value = BoardGameDetailUiState.Success(boardGameDetail)
                },
                onFailure = { error ->
                    _uiState.value = BoardGameDetailUiState.Error(error.message ?: "Unknown error")
                }
            )
        }
    }
    
    fun loadUsers() {
        viewModelScope.launch {
            try {
                Log.d("BoardGameDetailVM", "Loading users from Firebase")
                val firebaseUsers = getUsersFromFirebaseUseCase()
                val localPersons = getPersonsUseCase()
                
                // Combine Firebase users with existing local persons
                val allPersons = (firebaseUsers + localPersons).distinctBy { it.id }
                _persons.value = allPersons
                
                // Update current user person if needed
                if (_currentUserPerson.value == null) {
                    val currentUser = getCurrentUserUseCase()
                    _currentUserPerson.value = allPersons.find { it.email == currentUser?.email }
                }
                
                Log.d("BoardGameDetailVM", "Loaded ${allPersons.size} users")
            } catch (e: Exception) {
                Log.e("BoardGameDetailVM", "Error loading users", e)
            }
        }
    }
    
    fun addToMyCollection(boardGameDetail: BoardGameDetail, ownerId: Long?, holderId: Long?, notes: String?) {
        viewModelScope.launch {
            _addToCollectionStatus.value = AddToCollectionStatus.Loading
            
            try {
                val boardGame = BoardGame(
                    id = boardGameDetail.id,
                    name = boardGameDetail.name,
                    yearPublished = boardGameDetail.yearPublished,
                    description = boardGameDetail.description,
                    imageUrl = boardGameDetail.imageUrl,
                    type = boardGameDetail.type
                )
                
                // Use the current user's person as owner if no owner specified
                val owner = if (ownerId != null) {
                    _persons.value.find { it.id == ownerId }
                } else {
                    _currentUserPerson.value
                }
                
                val holder = if (holderId != null) _persons.value.find { it.id == holderId } else null
                
                addBoardGameToMyGamesUseCase(boardGame, owner, holder, notes)
                _addToCollectionStatus.value = AddToCollectionStatus.Success
            } catch (e: Exception) {
                Log.e("BoardGameDetailVM", "Error adding to collection", e)
                _addToCollectionStatus.value = AddToCollectionStatus.Error(e.message ?: "Unknown error")
            }
        }
    }
    
    fun resetAddToCollectionStatus() {
        _addToCollectionStatus.value = AddToCollectionStatus.Idle
    }
}

sealed class BoardGameDetailUiState {
    object Loading : BoardGameDetailUiState()
    data class Success(val boardGameDetail: BoardGameDetail) : BoardGameDetailUiState()
    data class Error(val message: String) : BoardGameDetailUiState()
}

sealed class AddToCollectionStatus {
    object Idle : AddToCollectionStatus()
    object Loading : AddToCollectionStatus()
    object Success : AddToCollectionStatus()
    data class Error(val message: String) : AddToCollectionStatus()
}
