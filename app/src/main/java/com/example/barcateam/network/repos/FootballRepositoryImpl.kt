package com.example.barcateam.network.repos

import com.example.barcateam.di.IoDispatcher
import com.example.barcateam.network.NetworkResult
import com.example.barcateam.network.api.FootballAPI
import com.example.barcateam.network.model.ApiResponse
import com.example.barcateam.network.model.PlayerResponse
import com.example.barcateam.network.safeApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FootballRepositoryImpl @Inject constructor(
    private val apiService: FootballAPI,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FootballRepository {

    override suspend fun getBarcaPlayers(): NetworkResult<ApiResponse<PlayerResponse>> {
        return withContext(ioDispatcher) {
            safeApiResult {
                apiService.getBarcaPlayers()
            }
        }
    }

    override suspend fun getStatsForPlayer(playerId: Long): NetworkResult<ApiResponse<PlayerResponse>> {
        return withContext(ioDispatcher) {
            safeApiResult { apiService.getPlayerStats(playerId) }
        }
    }
}