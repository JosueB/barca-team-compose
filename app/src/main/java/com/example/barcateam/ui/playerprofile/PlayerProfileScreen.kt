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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PlayerProfileScreen(
    modifier: Modifier = Modifier,
    playerId: Long,
    name: String,
    urlPhoto: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    with(sharedTransitionScope) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = urlPhoto,
                contentDescription = "Example Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "image-$playerId"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .aspectRatio(1f)
            )
            Text(
                text = "hello $name",
                modifier = Modifier
                    .fillMaxWidth()
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "text-$playerId"),
                        animatedVisibilityScope = animatedContentScope
                    )
            )
        }
    }
}