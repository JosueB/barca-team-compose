package com.example.barcateam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.barcateam.network.NetworkResult
import com.example.barcateam.network.model.Player
import com.example.barcateam.network.repos.FootballRepository
import com.example.barcateam.ui.navigation.PlayerRoute
import com.example.barcateam.ui.playerprofile.PlayerStatsUiState
import com.example.barcateam.ui.players.PlayersUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    footballRepository: FootballRepository
) : ViewModel() {

    val playerStatsUIState: StateFlow<PlayersUIState> = getPlayersUiState(
        footballRepository = footballRepository
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PlayersUIState.Loading
    )

    private fun getPlayersUiState(
        footballRepository: FootballRepository,
    ): Flow<PlayersUIState> {
        return flow {
            val response = footballRepository.getBarcaPlayers()
            if (response is NetworkResult.Success && response.data.response.isNotEmpty()) {
                emit(
                    PlayersUIState.Success(
                        response.data.response.map { it.player }
                    )
                )
            } else {
                emit(PlayersUIState.Error(Exception("getting data error")))
            }
        }
    }
}