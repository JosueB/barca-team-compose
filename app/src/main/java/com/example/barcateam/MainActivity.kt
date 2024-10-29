package com.example.barcateam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.barcateam.ui.BarcaAppBar
import com.example.barcateam.ui.theme.BarcaTeamTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val fooViewModel: PlayersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            fooViewModel.run()
            BarcaTeamTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BarcaAppBar(Modifier.padding(innerPadding))
                }
            }
        }
    }
}