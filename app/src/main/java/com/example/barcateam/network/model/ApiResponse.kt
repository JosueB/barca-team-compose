package com.example.barcateam.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    @SerialName("get") val get: String,
    @SerialName("parameters") val parameters: Parameters,
    @SerialName("errors") val errors: List<String>,
    @SerialName("results") val results: Int,
    @SerialName("paging") val paging: Paging,
    @SerialName("response") val response: List<T>
)

@Serializable
data class Parameters(
    @SerialName("team") val team: String,
    @SerialName("season") val season: String
)

@Serializable
data class Paging(
    @SerialName("current") val current: Int,
    @SerialName("total") val total: Int
)

@Serializable
data class PlayerResponse(
    @SerialName("player") val player: Player
)

@Serializable
data class Player(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("firstname") val firstname: String,
    @SerialName("lastname") val lastname: String,
    @SerialName("age") val age: Int,
    @SerialName("birth") val birth: Birth,
    @SerialName("nationality") val nationality: String,
    @SerialName("height") val height: String?,
    @SerialName("weight") val weight: String?,
    @SerialName("injured") val injured: Boolean,
    @SerialName("photo") val photo: String
)

@Serializable
data class Birth(
    @SerialName("date") val date: String,
    @SerialName("place") val place: String?,
    @SerialName("country") val country: String
)
