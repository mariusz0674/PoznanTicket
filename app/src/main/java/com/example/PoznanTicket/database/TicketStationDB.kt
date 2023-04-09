package com.example.PoznanTicket.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_ticket_stations")
data class TicketStationDB(
    @ColumnInfo
    val ida: String,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    val opis: String,

    @ColumnInfo
    val longitude: Double,

    @ColumnInfo
    val latitude: Double,

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L
)