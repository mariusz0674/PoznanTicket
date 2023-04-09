package com.example.PoznanTicket.network

import com.example.PoznanTicket.ticketstations.TicketStations
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://www.poznan.pl/mim/plan/"
private const val STATIONS_ENOPOINT = "map_service.html?mtype=pub_transport&co=class_objects&class_id=4000"
private const val QUERY_ENOPOINT = "map_service.html"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()




interface BikeStationApiService{
    @GET(STATIONS_ENOPOINT)
    suspend fun getBikeStations(): TicketStations

    @GET(QUERY_ENOPOINT)
    suspend fun getBikeStations(@Query("mtype") mType: String, @Query("co") co:String): TicketStations



}

object BikeStationApi{
    val retrofitService: BikeStationApiService by lazy{ retrofit.create(BikeStationApiService::class.java)}
}






//class BikeStationApiService {
//}