package com.dev.lamariposa.boardgamesassociation.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGame
import com.dev.lamariposa.boardgamesassociation.domain.model.MyBoardGame
import com.dev.lamariposa.boardgamesassociation.domain.model.Person
import com.dev.lamariposa.boardgamesassociation.domain.usecase.AddBoardGameToMyGamesUseCase
import com.dev.lamariposa.boardgamesassociation.domain.usecase.GetMyBoardGamesUseCase
import com.dev.lamariposa.boardgamesassociation.domain.usecase.RemoveBoardGameFromMyGamesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MyGamesViewModel(
    private val getMyBoardGamesUseCase: GetMyBoardGamesUseCase,
    private val removeBoardGameFromMyGamesUseCase: RemoveBoardGameFromMyGamesUseCase
) : ViewModel() {

    private val _myBoardGames = MutableStateFlow<List<MyBoardGame>>(emptyList())
    val myBoardGames: StateFlow<List<MyBoardGame>> = _myBoardGames.asStateFlow()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    init {
        loadMyBoardGames()
    }

    fun loadMyBoardGames() {
        viewModelScope.launch {
            _loading.value = true
            getMyBoardGamesUseCase()
                .catch { e ->
                    _error.postValue("Error loading games: ${e.message}")
                    _loading.value = false
                }
                .collect { games ->
                    _myBoardGames.value = games
                    _loading.value = false
                }
        }
    }

    fun removeBoardGameFromCollection(myBoardGameId: Long) {
        viewModelScope.launch {
            try {
                removeBoardGameFromMyGamesUseCase(myBoardGameId)
                // After removing, refresh the list
                loadMyBoardGames()
            } catch (e: Exception) {
                _error.postValue("Error removing game: ${e.message}")
            }
        }
    }
}