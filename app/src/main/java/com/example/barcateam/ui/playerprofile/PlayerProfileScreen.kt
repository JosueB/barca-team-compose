package com.example.barcateam.ui.playerprofile

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
                contentDescription = stringResource(R.string.image_of_player),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "image-${playerId}"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .aspectRatio(1f)
            )
            when (state) {
                is PlayerStatsUiState.Success -> {
                    PlayerDetailStats(state.player, state.statistics)
                }

                is PlayerStatsUiState.Loading -> {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.loading),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                is PlayerStatsUiState.Empty -> {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.no_info_player),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.weight(1f))
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