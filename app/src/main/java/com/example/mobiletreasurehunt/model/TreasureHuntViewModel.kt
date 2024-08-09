// Author: Steven Bertolucci
// Course: CS 492 - Mobile Application Development
// Institution: Oregon State University

package com.example.mobiletreasurehunt.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TreasureHuntViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TreasureHuntUiState())
    val uiState: StateFlow<TreasureHuntUiState> = _uiState

    fun updateClue(clue: String) {
        _uiState.value = _uiState.value.copy(selectedClue = clue)
    }

    var isStopwatchRunning by mutableStateOf(false)
        private set

    fun toggleStopwatch(isRunning: Boolean) {
        isStopwatchRunning = isRunning
    }
}

data class TreasureHuntUiState(
    val selectedClue: String? = null,
)