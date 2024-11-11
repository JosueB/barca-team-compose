package com.example.barcateam.network.api

import com.example.barcateam.network.NetworkConstants
import com.example.barcateam.network.model.ApiResponse
import com.example.barcateam.network.model.PlayerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FootballAPI {

    @GET("/players")
    suspend fun getBarcaPlayers(
        @Query("team") team: String = NetworkConstants.BARCELONA_ID,
        @Query("season") season: String = NetworkConstants.SEASON_2022
    ): Response<ApiResponse<PlayerResponse>>

    @GET("/players")
    suspend fun getPlayerStats(
        @Query("id") playerId: Long,
        @Query("team") team: String = NetworkConstants.BARCELONA_ID,
        @Query("season") season: String = NetworkConstants.SEASON_2022
    ): Response<ApiResponse<PlayerResponse>>
}