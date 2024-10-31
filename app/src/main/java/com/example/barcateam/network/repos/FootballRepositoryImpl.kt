package com.example.barcateam.network.repos

import com.example.barcateam.network.NetworkResult
import com.example.barcateam.network.api.FootballAPI
import com.example.barcateam.network.model.ApiResponse
import com.example.barcateam.network.model.PlayerResponse
import com.example.barcateam.network.safeApiResult
import javax.inject.Inject

class FootballRepositoryImpl @Inject constructor(
    private val apiService: FootballAPI
) : FootballRepository {

    override suspend fun getBarcaPlayers(): NetworkResult<ApiResponse<PlayerResponse>> {
        return safeApiResult { apiService.getBarcaPlayers() }
    }
}