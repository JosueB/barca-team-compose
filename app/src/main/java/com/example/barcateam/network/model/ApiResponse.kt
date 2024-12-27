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
    @SerialName("id") val playerId: Long?,
    @SerialName("season") val season: String
)

@Serializable
data class Paging(
    @SerialName("current") val current: Int,
    @SerialName("total") val total: Int
)

@Serializable
data class PlayerResponse(
    @SerialName("player") val player: Player,
    @SerialName("statistics") val statistics: List<Statistics>
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
    @SerialName("photo") val photo: String,
)

@Serializable
data class Birth(
    @SerialName("date") val date: String,
    @SerialName("place") val place: String?,
    @SerialName("country") val country: String
)

// Statistics data classes
@Serializable
data class Statistics(
    @SerialName("team") val team: Team,
    @SerialName("league") val league: League,
    @SerialName("games") val games: Games,
    @SerialName("goals") val goals: Goals,
    @SerialName("shots") val shots: Shots,
    @SerialName("passes") val passes: Passes
)

@Serializable
data class Team(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("logo") val logo: String
)

@Serializable
data class League(
    @SerialName("id") val id: Long?,
    @SerialName("name") val name: String?,
    @SerialName("country") val country: String?,
    @SerialName("logo") val logo: String?,
    @SerialName("flag") val flag: String?,
    @SerialName("season") val season: Int?
)

@Serializable
data class Games(
    @SerialName("appearences") val appearances: Int?,
    @SerialName("lineups") val lineups: Int?,
    @SerialName("minutes") val minutes: Int?,
    @SerialName("number") val number: Int? = null,
    @SerialName("position") val position: String?,
    @SerialName("rating") val rating: String?,
    @SerialName("captain") val captain: Boolean?
)

@Serializable
data class Goals(
    @SerialName("total") val total: Int?,
    @SerialName("conceded") val conceded: Int? = null,
    @SerialName("assists") val assists: Int? = null,
    @SerialName("saves") val saves: Int? = null
)

@Serializable
data class Shots(
    @SerialName("total") val total: Int?,
    @SerialName("on") val on: Int?
)

@Serializable
data class Passes(
    @SerialName("total") val total: Int?,
    @SerialName("key") val key: Int?,
    @SerialName("accuracy") val accuracy: Int?
)
