package com.example.PoznanTicket.ticketstations
import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Properties(
    @Json(name = "nazwa")
    val name: String,
    @Json(name = "kolejnosc")
    val kolejnosc: String,
    @Json(name = "url")
    val freeRacks: String,
    @Json(name = "opis")
    val opis: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(kolejnosc)
        parcel.writeString(freeRacks)
        parcel.writeString(opis)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Properties> {
        override fun createFromParcel(parcel: Parcel): Properties {
            return Properties(parcel)
        }

        override fun newArray(size: Int): Array<Properties?> {
            return arrayOfNulls(size)
        }
    }
}