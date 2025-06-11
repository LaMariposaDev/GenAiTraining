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
import com.dev.lamariposa.boardgamesassociation.domain.usecase.AddPersonUseCase
import com.dev.lamariposa.boardgamesassociation.domain.usecase.GetBoardGameDetailsUseCase
import com.dev.lamariposa.boardgamesassociation.domain.usecase.GetPersonsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BoardGameDetailViewModel(
    private val getBoardGameDetailsUseCase: GetBoardGameDetailsUseCase,
    private val addBoardGameToMyGamesUseCase: AddBoardGameToMyGamesUseCase,
    private val getPersonsUseCase: GetPersonsUseCase,
    private val addPersonUseCase: AddPersonUseCase
) : ViewModel() {
    
    private val _uiState = MutableLiveData<BoardGameDetailUiState>(BoardGameDetailUiState.Loading)
    val uiState: LiveData<BoardGameDetailUiState> = _uiState
    
    private val _persons = MutableStateFlow<List<Person>>(emptyList())
    val persons: StateFlow<List<Person>> = _persons.asStateFlow()
    
    private val _addToCollectionStatus = MutableStateFlow<AddToCollectionStatus>(AddToCollectionStatus.Idle)
    val addToCollectionStatus: StateFlow<AddToCollectionStatus> = _addToCollectionStatus.asStateFlow()
    
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
    
    fun loadPersons() {
        viewModelScope.launch {
            try {
                _persons.value = getPersonsUseCase()
            } catch (e: Exception) {
                // Handle error
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
                
                val owner = if (ownerId != null) _persons.value.find { it.id == ownerId } else null
                val holder = if (holderId != null) _persons.value.find { it.id == holderId } else null
                
                addBoardGameToMyGamesUseCase(boardGame, owner, holder, notes)
                _addToCollectionStatus.value = AddToCollectionStatus.Success
            } catch (e: Exception) {
                Log.e("BoardGameDetailViewModel", "Error adding to collection", e)
                _addToCollectionStatus.value = AddToCollectionStatus.Error(e.message ?: "Unknown error")
            }
        }
    }
    
    fun resetAddToCollectionStatus() {
        _addToCollectionStatus.value = AddToCollectionStatus.Idle
    }
    
    suspend fun addPerson(person: Person): Long {
        return try {
            addPersonUseCase(person)
        } catch (e: Exception) {
            throw e
        }
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
