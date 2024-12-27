package com.example.barcateam.ui.playerprofile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.barcateam.R
import com.example.barcateam.network.model.Birth
import com.example.barcateam.network.model.Games
import com.example.barcateam.network.model.Goals
import com.example.barcateam.network.model.League
import com.example.barcateam.network.model.Passes
import com.example.barcateam.network.model.Player
import com.example.barcateam.network.model.Shots
import com.example.barcateam.network.model.Statistics
import com.example.barcateam.network.model.Team

@Composable
fun PlayerDetailStats(player: Player, statistics: Statistics?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = stringResource(R.string.la_liga_season),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))
        HorizontalDivider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatInfoTile(
                "${statistics?.goals?.total ?: "--"}",
                stringResource(R.string.total_goals_lbl)
            )
            StatInfoTile(
                "${statistics?.shots?.total ?: "--"}",
                stringResource(R.string.total_shots_lbl)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatInfoTile(
                "${statistics?.games?.minutes ?: "--"}",
                stringResource(R.string.total_minutes_lbl)
            )
            StatInfoTile(
                "${statistics?.games?.appearances ?: "--"}",
                stringResource(R.string.appearances_lbl)
            )
        }
    }
}

@Composable
fun StatInfoTile(value: String, label: String) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .sizeIn(minWidth = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSkylineLoopInfo() {
    val player = Player(
        id = 9092L, name = "Leo Messi", firstname = "Nicole", lastname = "Maritza", age = 7681,
        birth = Birth(date = "Brenton", place = null, country = "Octavia"),
        nationality = "Darious", height = null, weight = null, injured = true, photo = "Evie"
    )
    val statistics = Statistics(
        team = Team(id = 6029L, name = "Barcelona", logo = "Dominque"),
        league = League(
            id = null,
            name = null,
            country = null,
            logo = null,
            flag = null,
            season = null
        ), games = Games(
            appearances = null,
            lineups = null,
            minutes = null,
            number = null,
            position = null,
            rating = null,
            captain = null
        ),
        goals = Goals(total = null, conceded = null, assists = null, saves = null),
        shots = Shots(total = null, on = null),
        passes = Passes(
            total = null, key = null, accuracy = null
        )
    )
    MaterialTheme {
        PlayerDetailStats(player, statistics)
    }
}