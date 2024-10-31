package com.example.barcateam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barcateam.network.NetworkResult
import com.example.barcateam.network.repos.FootballRepository
import com.example.barcateam.ui.players.PlayersUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    private val footballRepository: FootballRepository
) : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow<PlayersUIState>(PlayersUIState.Loading)
    val uiState: StateFlow<PlayersUIState> = _uiState.asStateFlow()

    init {
        run()
    }

    fun run() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = footballRepository.getBarcaPlayers()
            if (response is NetworkResult.Success) {
                _uiState.update {
                    PlayersUIState.Success(response.data.response.map { it.player })
                }
            }
        }
    }
}