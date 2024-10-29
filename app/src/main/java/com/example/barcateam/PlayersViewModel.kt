package com.example.barcateam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barcateam.network.api.FootballAPI
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
    private val footballAPI: FootballAPI
) : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow<PlayersUIState>(PlayersUIState.Loading)
    val uiState: StateFlow<PlayersUIState> = _uiState.asStateFlow()

    init {
        run()
    }

    fun run() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = footballAPI.getBarcaPlayers()
            if (response.isSuccessful) {

                val list = response.body()?.response?.map {
                    it.player
                }.orEmpty()

                _uiState.update {
                    PlayersUIState.Success(list)
                }
            }
        }
    }
}