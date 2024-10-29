package com.example.barcateam.ui.players

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.barcateam.PlayersViewModel

@Composable
fun BarcaPlayersScreen(
    modifier: Modifier = Modifier,
    playersViewModel: PlayersViewModel,
    onCardClicked: () -> Unit
) {

    val playersUIState by playersViewModel.uiState.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {

        when (val state = playersUIState) {
            is PlayersUIState.Success -> {
                items(
                    items = state.playersFeed,
                    key = { player ->
                        player.id
                    }
                ) { player ->
                    PlayerCard(player) {
                        onCardClicked()
                    }
                }
            }
            is PlayersUIState.Loading -> {
            }
        }
    }
}