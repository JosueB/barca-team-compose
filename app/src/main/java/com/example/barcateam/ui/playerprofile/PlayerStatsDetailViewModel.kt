package com.example.barcateam.ui.playerprofile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.barcateam.network.NetworkResult
import com.example.barcateam.network.repos.FootballRepository
import com.example.barcateam.ui.navigation.PlayerRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlayerStatsDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    footballRepository: FootballRepository
): ViewModel() {

    private val playerId = savedStateHandle.toRoute<PlayerRoute>().playerId

    val playerStatsUIState: StateFlow<PlayerStatsUiState> = getPlayerUiState(
        playerId = playerId,
        footballRepository = footballRepository,
    ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PlayerStatsUiState.Loading
    )

    private fun getPlayerUiState(
        playerId: Long,
        footballRepository: FootballRepository,
    ): Flow<PlayerStatsUiState> {
        return flow {
            val response = footballRepository.getStatsForPlayer(playerId = playerId)
            if (response is NetworkResult.Success && response.data.response.isNotEmpty()) {
                emit(
                    PlayerStatsUiState.Success(
                        response.data.response.first().player,
                        response.data.response.firstOrNull()?.statistics?.firstOrNull()
                    )
                )
            } else {
                emit(PlayerStatsUiState.Error(Exception("getting data error")))
            }
        }
    }
}