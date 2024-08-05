// Author: Steven Bertolucci
// Course: CS 492 - Mobile Application Development
// Institution: Oregon State University

package com.example.mobiletreasurehunt.model

import android.health.connect.datatypes.ExerciseRoute
import android.location.Location
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TreasureHuntViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TreasureHuntUiState())
    val uiState: StateFlow<TreasureHuntUiState> = _uiState

    fun updateClue(clue: String) {
        _uiState.value = _uiState.value.copy(selectedClue = clue)
    }

    fun reset() {
        _uiState.value = TreasureHuntUiState()
    }
}

data class TreasureHuntUiState(
    val selectedClue: String? = null,
)