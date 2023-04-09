package com.example.PoznanTicket.ticketstations
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TicketStations(
    @Json(name = "features")
    val items: List<TicketStation>
)