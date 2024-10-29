package com.example.barcateam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.barcateam.PlayersViewModel
import com.example.barcateam.ui.playerprofile.PlayerProfileScreen
import com.example.barcateam.ui.players.BarcaPlayersScreen
import kotlinx.serialization.Serializable

@Serializable
object PlayerRoute
@Serializable
object PlayersListRoute

@Composable
fun BarcelonaAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = PlayersListRoute

    ) {
        composable<PlayerRoute> {
            PlayerProfileScreen(
                onNavigateToPlayers = { navController.navigate(route = PlayersListRoute) },
            )
        }
        composable<PlayersListRoute> {
            val viewModel = hiltViewModel<PlayersViewModel>()
            BarcaPlayersScreen(playersViewModel = viewModel) {
                navController.navigate(route = PlayerRoute)
            }
        }
    }
}