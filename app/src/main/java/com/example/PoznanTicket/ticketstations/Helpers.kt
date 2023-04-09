package com.example.PoznanTicket.ticketstations

import android.content.Context
import android.os.Build
import com.example.PoznanTicket.database.TicketStationDB

//import com.example.PoznanTicket.database.BikeStationDB


// Set of helper functions that are common for entire project. A singleton pattern is used here,
// so we don't need to create an instance of a Helper class
object Helpers {
    @Suppress("DEPRECATION")
    fun getColor(context: Context, colorId: Int): Int {
        // Custom implementation of the getColor method that disables the deprecation warning
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getColor(colorId)
        } else {
            context.resources.getColor(colorId) // for older devices (older than API 23) to get the
                                                // color we first had to access resources
        }
    }

     //Function that converts the TicketStation (Retrofit version) to BikeStationDB (database version)
    fun createTicketStationDB(bikeStation: TicketStation): TicketStationDB {
        return TicketStationDB(
            bikeStation.id,
            bikeStation.properties.name,
            bikeStation.properties.opis,
            //bikeStation.properties.updated,
            bikeStation.geometry.coordinates[0],
            bikeStation.geometry.coordinates[1]
        )
    }
    // Function that converts the BikeStationSB  (database version)  to TicketStation (Retrofit version)
    fun createTicketStation(ticketStationDB: TicketStationDB): TicketStation {
        val coors = listOf<Double>(ticketStationDB.longitude, ticketStationDB.latitude)
        return TicketStation(
            Geometry(coors, "Point"),
            ticketStationDB.ida.toString(),
            Properties(
                ticketStationDB.name.toString(),
                1.toString(),
                1.toString(),
                ticketStationDB.opis
                //bikeStationDB.updated
            ),
            "Feature"
        )
    }

    fun createTicketStationList(ticketStationsDB: List<TicketStationDB>): ArrayList<TicketStation> {
        val arrayList = ArrayList<TicketStation>()
        for (ticketStationDB in ticketStationsDB) {
            arrayList.add(createTicketStation(ticketStationDB))
        }
        return arrayList
    }



}



