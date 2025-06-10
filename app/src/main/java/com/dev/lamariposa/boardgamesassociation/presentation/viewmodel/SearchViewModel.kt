package com.dev.lamariposa.boardgamesassociation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGame
import com.dev.lamariposa.boardgamesassociation.domain.usecase.SearchBoardGamesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class SearchUiState {
    object Initial : SearchUiState()
    object Loading : SearchUiState()
    data class Success(val games: List<BoardGame>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}

class SearchViewModel(
    private val searchBoardGamesUseCase: SearchBoardGamesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val uiState: StateFlow<SearchUiState> = _uiState

    fun search(query: String) {
        if (query.isBlank()) {
            _uiState.value = SearchUiState.Initial
            return
        }
        
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            try {
                val results = searchBoardGamesUseCase(query)
                if (results.isEmpty()) {
                    _uiState.value = SearchUiState.Error("No games found matching '$query'")
                } else {
                    _uiState.value = SearchUiState.Success(results)
                }
            } catch (e: Exception) {
                _uiState.value = SearchUiState.Error("Error: ${e.localizedMessage ?: "Unknown error"}")
            }
        }
    }
}
