package com.example.barcateam.ui.players

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barcateam.network.NetworkResult
import com.example.barcateam.network.repos.FootballRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
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