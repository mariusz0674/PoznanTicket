package com.example.PoznanTicket.ticketstations
import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Geometry(
    @Json(name = "coordinates")
    val coordinates: List<Double>,
    @Json(name = "type")
    val type: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createDoubleList(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDoubleList(coordinates)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Geometry> {
        override fun createFromParcel(parcel: Parcel): Geometry {
            return Geometry(parcel)
        }

        override fun newArray(size: Int): Array<Geometry?> {
            return arrayOfNulls(size)
        }
    }
}
fun Parcel.writeDoubleList(list : List<Double>){
    writeDoubleArray(list.toDoubleArray())
}

fun Parcel.createDoubleList(): List<Double>
{
    val d_a:DoubleArray = doubleArrayOf()
    readDoubleArray(d_a)
    return d_a.toList()
}

