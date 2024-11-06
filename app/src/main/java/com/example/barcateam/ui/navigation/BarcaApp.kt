package com.example.barcateam.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.barcateam.PlayersViewModel
import com.example.barcateam.R
import com.example.barcateam.ui.playerprofile.PlayerProfileScreen
import com.example.barcateam.ui.players.BarcaPlayersScreen
import kotlinx.serialization.Serializable

@Serializable
data class PlayerRoute(
    val name: String,
    val age: Int,
    val nationality: String,
    val urlPhoto: String,
    val playerId: Int
)

@Serializable
object PlayersListRoute

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun BarcaApp() {

    val topBarState = rememberSaveable { (mutableStateOf(true)) }
    val navController = rememberNavController()

    // Toolbar visibility state
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (topBarState.value) {
                BarcelonaToolBar(scrollBehavior)
            }
        }
    ) { innerPadding ->

        SharedTransitionLayout {
            NavHost(
                navController = navController,
                startDestination = PlayersListRoute

            ) {
                composable<PlayerRoute> {
                    topBarState.value = false
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
                    topBarState.value = true
                    val viewModel = hiltViewModel<PlayersViewModel>()
                    BarcaPlayersScreen(
                        modifier = Modifier.padding(innerPadding),
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarcelonaToolBar(scrollBehavior: TopAppBarScrollBehavior) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                stringResource(R.string.tool_bar_name),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        scrollBehavior = scrollBehavior
    )
}