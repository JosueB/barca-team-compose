package com.example.barcateam.ui.playerprofile

import com.example.barcateam.network.model.Player
import com.example.barcateam.network.model.Statistics

/**
 * A sealed hierarchy describing the state of the players.
 */
sealed interface PlayerStatsUiState {
    /**
     * The players screen loading.
     */
    data object Loading : PlayerStatsUiState

    data class Error(val ex: Exception) : PlayerStatsUiState

    /**
     * The players data
     */
    data class Success(
        val player: Player,
        val statistics: Statistics?
    ) : PlayerStatsUiState
}