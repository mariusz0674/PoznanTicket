package com.example.PoznanTicket.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TicketStationDB::class], version = 1, exportSchema = false)

abstract class TicketStationDatabase: RoomDatabase() {
    abstract val ticketStationDatabaseDao: TicketStationDAO

    companion object{
        @Volatile
        private var INSTANCE: TicketStationDatabase? = null
        fun getInstance(context: Context):TicketStationDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TicketStationDatabase::class.java,
                        "saved_ticket_station"
                    ).fallbackToDestructiveMigration().build()
                }
                return instance
            }
        }
    }





}