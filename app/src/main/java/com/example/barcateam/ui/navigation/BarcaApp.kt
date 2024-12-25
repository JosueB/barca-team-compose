package com.example.barcateam.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.barcateam.R
import com.example.barcateam.ui.playerprofile.PlayerProfileRoute
import com.example.barcateam.ui.players.BarcaPlayersRoute
import kotlinx.serialization.Serializable

@Serializable
data class PlayerRoute(
    val playerId: Long,
    val photoUrl: String
)

@Serializable
object PlayersListRoute

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun BarcaApp() {

    val navController = rememberNavController()

    // Toolbar visibility state
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { BarcelonaToolBar(scrollBehavior) }
    ) { innerPadding ->

        SharedTransitionLayout {
            NavHost(
                navController = navController,
                startDestination = PlayersListRoute,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable<PlayerRoute> {
                    val args = it.toRoute<PlayerRoute>()
                    PlayerProfileRoute(
                        playerId = args.playerId,
                        photoUrl = args.photoUrl,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this@composable
                    )
                }
                composable<PlayersListRoute> {
                    BarcaPlayersRoute(
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this@composable
                    ) { playerId, photoUrl ->
                        navController.navigate(
                            route = PlayerRoute(
                                playerId,
                                photoUrl
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
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        title = {
            Image(
                painter = painterResource(id = R.drawable.ic_barcelona),
                contentDescription = stringResource(id = R.string.ic_barcelona)
            )
        },
        scrollBehavior = scrollBehavior
    )
}