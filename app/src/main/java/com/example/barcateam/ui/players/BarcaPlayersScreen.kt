@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.barcateam.ui.players

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.barcateam.R
import com.example.barcateam.ui.generic.GenericMessageScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BarcaPlayersRoute(
    modifier: Modifier = Modifier,
    playersViewModel: PlayersViewModel = hiltViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onCardClicked: (playerId: Long, photoUrl: String) -> Unit
) {

    val playersUIState by playersViewModel.playerStatsUIState.collectAsStateWithLifecycle()

    BarcaPlayersScreen(
        modifier = modifier,
        playersUIState = playersUIState,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        onCardClicked = onCardClicked
    )
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BarcaPlayersScreen(
    modifier: Modifier = Modifier,
    playersUIState: PlayersUIState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onCardClicked: (playerId: Long, photoUrl: String) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 200.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier
    ) {

        when (val state = playersUIState) {
            is PlayersUIState.Success -> {
                if (state.playersFeed.isEmpty()) {
                    item {
                        GenericMessageScreen(
                            imageResId = android.R.drawable.ic_dialog_alert, // Replace with your drawable resource
                            title = stringResource(R.string.lbl_empty)
                        )
                    }
                } else {
                    items(
                        items = state.playersFeed,
                        key = { player ->
                            player.id
                        }
                    ) { player ->
                        PlayerCard(
                            player,
                            sharedTransitionScope,
                            animatedContentScope
                        ) { playerId, photoUrl ->
                            onCardClicked(playerId, photoUrl)
                        }
                    }
                }
            }

            is PlayersUIState.Loading -> {
                items(7) {
                    ShimmerPlayerCard()
                }
            }

            is PlayersUIState.Error -> {
                item {
                    GenericMessageScreen(
                        imageResId = android.R.drawable.ic_dialog_alert, // Replace with your drawable resource
                        title = stringResource(R.string.lbl_error),
                        description = stringResource(R.string.lbl_error_description),
                    ) {

                    }
                }
            }
        }
    }
}