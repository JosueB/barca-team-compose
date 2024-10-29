package com.example.barcateam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barcateam.network.api.FootballAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    private val footballAPI: FootballAPI
) : ViewModel() {

    fun run() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = footballAPI.getBarcaPlayers()
            if (response.isSuccessful) {
                response.body()?.response?.forEach {
                    println(it.player.name)
                }
            }
        }
    }
}