package com.example.barcateam.ui.players

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.barcateam.network.model.Player

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PlayerCard(
    player: Player,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onCardClicked: (playerId: Long, photoUrl: String) -> Unit
) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .size(width = 240.dp, height = 100.dp)
            .clickable {
                onCardClicked(player.id, player.photo)
            }
    ) {
        with(sharedTransitionScope) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {

                AsyncImage(
                    model = player.photo,
                    contentDescription = "Example Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .sharedElement(
                            sharedTransitionScope.rememberSharedContentState(key = "image-${player.id}"),
                            animatedVisibilityScope = animatedContentScope
                        )
                        .size(70.dp)
                        .clip(CircleShape)
                )

                Column(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = player.name,
                        fontSize = 12.sp,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .sharedElement(
                                sharedTransitionScope.rememberSharedContentState(key = "text-${player.id}"),
                                animatedVisibilityScope = animatedContentScope
                            )
                    )

                    Text(
                        text = "${player.age}",
                        fontSize = 12.sp,
                        maxLines = 1,
                    )

                    Text(
                        text = player.nationality,
                        fontSize = 12.sp,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}