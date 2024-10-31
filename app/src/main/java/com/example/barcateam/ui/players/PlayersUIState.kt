package com.example.barcateam.ui.players

import com.example.barcateam.network.model.Player

/**
 * A sealed hierarchy describing the state of the players.
 */
sealed interface PlayersUIState {
    /**
     * The players screen loading.
     */
    data object Loading : PlayersUIState

    data class Error(val ex: Exception) : PlayersUIState

    /**
     * The players data
     */
    data class Success(val playersFeed: List<Player>) : PlayersUIState
}