package com.dev.lamariposa.boardgamesassociation.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGameDetail
import com.dev.lamariposa.boardgamesassociation.domain.usecase.GetBoardGameDetailsUseCase
import kotlinx.coroutines.launch

class BoardGameDetailViewModel(
    private val getBoardGameDetailsUseCase: GetBoardGameDetailsUseCase
) : ViewModel() {
    
    private val _uiState = MutableLiveData<BoardGameDetailUiState>(BoardGameDetailUiState.Loading)
    val uiState: LiveData<BoardGameDetailUiState> = _uiState
    
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
}

sealed class BoardGameDetailUiState {
    object Loading : BoardGameDetailUiState()
    data class Success(val boardGameDetail: BoardGameDetail) : BoardGameDetailUiState()
    data class Error(val message: String) : BoardGameDetailUiState()
}
