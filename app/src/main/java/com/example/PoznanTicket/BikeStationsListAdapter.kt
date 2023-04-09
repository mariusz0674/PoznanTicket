package com.example.PoznanTicket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.PoznanTicket.ticketstations.TicketStation
import poznanticket.R

class BikeStationsListAdapter(
    private val stations: ArrayList<TicketStation>,
    private val mListener: BikeStationListEventListener): RecyclerView.Adapter<BikeStationsListAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        // Setup the Views in ViewHolder
        val container: View = view.rootView
        val bikeImg: ImageView = view.findViewById<ImageView>(R.id.bikeImg)
      //  val parkImg: ImageView = view.findViewById<ImageView>(R.id.parkImg)
        val bikeNumber: TextView = view.findViewById<TextView>(R.id.bikeNumber)
       // val rackNumber: TextView = view.findViewById<TextView>(R.id.rackNumber)
        val stationName: TextView = view.findViewById<TextView>(R.id.stationName)
        val updateTime: TextView = view.findViewById<TextView>(R.id.updateTime)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Populate the displayed elements and assign a listener for a click event
        val item = stations[position]
        //val numBikes: Int = item.properties.kolejnosc.toInt()
        //holder.bikeNumber.text = item.properties.kolejnosc
        holder.bikeNumber.text = item.id.toString()
       val context = holder.container.context
        // The number of kolejnosc and racks impact the color of icons in each list entry
//        when(numBikes){
//            0 -> holder.bikeImg.setColorFilter(Helpers.getColor(context, R.color.my_red))
//            in 1..5 -> holder.bikeImg.setColorFilter(Helpers.getColor(context, R.color.my_orange))
//            else -> holder.bikeImg.setColorFilter(Helpers.getColor(context, R.color.my_green))
//        }
//        val numRacks: Int = item.properties.freeRacks.toInt()
//        holder.rackNumber.text = item.properties.freeRacks
//        when(numRacks){
//            0 -> holder.parkImg.setColorFilter(Helpers.getColor(context, R.color.my_red))
//            in 1..5 -> holder.parkImg.setColorFilter(Helpers.getColor(context, R.color.my_orange))
//            else -> holder.parkImg.setColorFilter(Helpers.getColor(context, R.color.my_green))
//        }
        var opistxt = item.properties.name
        holder.stationName.text = opistxt
       // holder.updateTime.text = item.properties.updated

        // Delegate onClick event listener to mListener BikeStationListEventListener interface
        holder.container.setOnClickListener {
            mListener.clickListener(position,item)
        }
    }

    override fun getItemCount(): Int = stations.size

    fun clearList(){
        stations.clear()
        notifyDataSetChanged()
    }
}
interface BikeStationListEventListener{
    fun clickListener(position: Int, ticketStation: TicketStation)
}