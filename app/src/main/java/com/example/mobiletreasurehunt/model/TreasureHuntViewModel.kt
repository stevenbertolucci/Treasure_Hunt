package com.example.mobiletreasurehunt.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TreasureHuntViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TreasureHuntUiState())
    val uiState: StateFlow<TreasureHuntUiState> = _uiState

    fun updateClue(category: String) {
        _uiState.value = _uiState.value.copy(selectedClue = category)
    }

    fun reset() {
        _uiState.value = TreasureHuntUiState()
    }
}

data class TreasureHuntUiState(
    val selectedClue: String? = null,
)