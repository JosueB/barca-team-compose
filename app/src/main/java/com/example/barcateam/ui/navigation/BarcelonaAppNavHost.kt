package com.example.barcateam.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.barcateam.PlayersViewModel
import com.example.barcateam.ui.playerprofile.PlayerProfileScreen
import com.example.barcateam.ui.players.BarcaPlayersScreen
import kotlinx.serialization.Serializable

@Serializable
data class PlayerRoute( // TODO: Don't pass all of these args here, only pass the id
    val name: String,
    val age: Int,
    val nationality: String,
    val urlPhoto: String,
    val playerId: Int
)

@Serializable
object PlayersListRoute

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BarcelonaAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    SharedTransitionLayout {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = PlayersListRoute

        ) {
            composable<PlayerRoute> {
                val args = it.toRoute<PlayerRoute>()
                PlayerProfileScreen(
                    name = args.name,
                    age = args.age,
                    nationality = args.nationality,
                    urlPhoto = args.urlPhoto,
                    playerId = args.playerId,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }
            composable<PlayersListRoute> {
                val viewModel = hiltViewModel<PlayersViewModel>()
                BarcaPlayersScreen(
                    playersViewModel = viewModel,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                ) { name, age, nationality, urlPhoto, playerId ->
                    navController.navigate(
                        route = PlayerRoute(
                            name,
                            age,
                            nationality,
                            urlPhoto,
                            playerId
                        )
                    )
                }
            }
        }
    }
}