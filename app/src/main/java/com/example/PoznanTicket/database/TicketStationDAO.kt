package com.example.PoznanTicket.database

import androidx.room.*

@Dao
interface TicketStationDAO {
    @Insert
    fun insert(bikeStation: TicketStationDB)

    @Update
    fun update(bikeStation: TicketStationDB)

    @Query("SELECT * FROM saved_ticket_stations")
    fun getAll() : List<TicketStationDB>

    @Query("SELECT * FROM saved_ticket_stations WHERE ida = :label")
    fun getByLabel(label: String): List<TicketStationDB>

    @Query("DELETE FROM saved_ticket_stations")
    fun clear()

    @Delete
    fun delete(bikeStation: TicketStationDB)



}