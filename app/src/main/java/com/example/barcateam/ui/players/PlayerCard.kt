package com.example.barcateam.ui.players

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.barcateam.network.model.Birth
import com.example.barcateam.network.model.Player

@Composable
fun PlayerCard(player: Player, onCardClicked: () -> Unit) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .size(width = 240.dp, height = 100.dp)
            .clickable {
                onCardClicked()
            }
    ) {
        Text(
            text = player.name,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun PlayerCardPreview() {
    PlayerCard(
        Player(
            id = 6308,
            name = "Sir",
            firstname = "Rickie",
            lastname = "Randon",
            age = 7784,
            birth = Birth(
                date = "",
                place = null,
                country = "",
            ),
            nationality = "Obinna",
            height = null,
            weight = null,
            injured = true,
            photo = "Antwanette"
        )
    ) {}
}