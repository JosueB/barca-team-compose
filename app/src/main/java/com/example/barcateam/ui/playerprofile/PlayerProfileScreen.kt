package com.example.barcateam.ui.playerprofile

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.barcateam.R
import com.example.barcateam.ui.generic.GenericMessageScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PlayerProfileRoute(
    modifier: Modifier = Modifier,
    playerId: Long,
    photoUrl: String,
    playerStatsDetailViewModel: PlayerStatsDetailViewModel = hiltViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val playerStatsUiState by playerStatsDetailViewModel.playerStatsUIState.collectAsStateWithLifecycle()

    PlayerProfileScreen(
        modifier,
        playerId,
        photoUrl,
        playerStatsUiState,
        sharedTransitionScope,
        animatedContentScope
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PlayerProfileScreen(
    modifier: Modifier = Modifier,
    playerId: Long,
    currentPhotoUrl: String,
    state: PlayerStatsUiState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    with(sharedTransitionScope) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {

            AsyncImage(
                model = currentPhotoUrl,
                contentDescription = "Example Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "image-${playerId}"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .aspectRatio(1f)
            )

            Text(
                text = "hello ${(state as? PlayerStatsUiState.Success)?.player?.name.orEmpty()}",
                modifier = Modifier
                    .fillMaxWidth()
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "text-${playerId}"),
                        animatedVisibilityScope = animatedContentScope
                    )
            )
            when (state) {
                is PlayerStatsUiState.Success -> {
                    Text(
                        text = "Goals: ${state.statistics?.goals?.total ?: 0}",
                    )
                    Text(
                        text = "Shots: ${state.statistics?.shots?.total ?: 0}",
                    )
                }

                is PlayerStatsUiState.Loading -> {
                    Text("loading")

                }

                is PlayerStatsUiState.Error -> {
                    GenericMessageScreen(
                        imageResId = android.R.drawable.ic_dialog_alert, // Replace with your drawable resource
                        title = stringResource(R.string.lbl_error),
                        description = stringResource(R.string.lbl_error_description),
                    ) {
                        // TODO: handle retries, notify viewmodel
                    }
                }
            }
        }
    }
}