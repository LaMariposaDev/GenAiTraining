package com.dev.lamariposa.boardgamesassociation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGame
import com.dev.lamariposa.boardgamesassociation.domain.usecase.SearchBoardGamesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchBoardGamesUseCase: SearchBoardGamesUseCase
) : ViewModel() {

    private val _results = MutableStateFlow<List<BoardGame>>(emptyList())
    val results: StateFlow<List<BoardGame>> = _results

    fun search(query: String) {
        viewModelScope.launch {
            _results.value = searchBoardGamesUseCase(query)
        }
    }
}
