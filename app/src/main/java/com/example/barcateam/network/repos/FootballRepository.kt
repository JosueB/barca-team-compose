package com.example.barcateam.network.repos

import com.example.barcateam.network.NetworkResult
import com.example.barcateam.network.model.ApiResponse
import com.example.barcateam.network.model.PlayerResponse

interface FootballRepository {

    suspend fun getBarcaPlayers(): NetworkResult<ApiResponse<PlayerResponse>>

}