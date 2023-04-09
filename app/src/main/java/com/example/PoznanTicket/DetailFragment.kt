package com.example.PoznanTicket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.PoznanTicket.database.TicketStationDatabase
import com.example.PoznanTicket.ticketstations.Helpers
import com.example.PoznanTicket.ticketstations.TicketStation
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
//import com.example.PoznanTicket.databinding.FragmentDetailBinding
import poznanticket.R
import poznanticket.databinding.FragmentDetailBinding

//import poznanticket.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    // view binding variable that allows for easy access to the Fragments' Views
    lateinit var binding: FragmentDetailBinding

    val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailBinding.inflate(inflater, container, false) // setup the binding variable
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ticketStation: TicketStation = args.ticketStation
        val numBikes: Int = ticketStation.properties.kolejnosc.toInt()
        // Display the details of the selected TicketStation - access to all Views is made with
        // View Binding via binding variable
        binding.stationNumberDetail.text = ticketStation.properties.name

//        when(numBikes){
//            0 -> binding.bikeImgDetail.setColorFilter(Helpers.getColor(requireContext(), R.color.my_red))
//            in 1..5 -> binding.bikeImgDetail.setColorFilter(Helpers.getColor(requireContext(), R.color.my_orange))
//            else -> binding.bikeImgDetail.setColorFilter(Helpers.getColor(requireContext(), R.color.my_green))
//        }
//        val numRacks: Int = bikeStation.properties.freeRacks.toInt()
//        binding.rackNumberDetail.text = bikeStation.properties.freeRacks
//        when(numRacks){
//            0 -> binding.parkImgDetail.setColorFilter(Helpers.getColor(requireContext(), R.color.my_red))
//            in 1..5 -> binding.parkImgDetail.setColorFilter(Helpers.getColor(requireContext(), R.color.my_orange))
//            else -> binding.parkImgDetail.setColorFilter(Helpers.getColor(requireContext(), R.color.my_green))
//        }
        var opistxt = ticketStation.properties.opis
        opistxt = opistxt.replace("<p>","")
        opistxt = opistxt.replace("<p style=\"text-align: center\">","")
        opistxt = opistxt.replace("</p>...","")
        binding.stationNameDetail.text = opistxt
       // binding.updateTimeDetail.text = bikeStation.properties.updated
        val longitude = ticketStation.geometry.coordinates[0]
        val latitude = ticketStation.geometry.coordinates[1]

        binding.coordinatesDetail.text = getString(R.string.coordinates).format(
            "Lat:" , latitude, "Lng:", longitude)
    }

    fun  saveToDatabase(){
        val ticketStationDao =
            TicketStationDatabase.getInstance(requireContext()).ticketStationDatabaseDao

        GlobalScope.launch {
            withContext(Dispatchers.IO){
                val byLabel = ticketStationDao.getByLabel(args.ticketStation.id)
                val op = if(byLabel.isEmpty()){
                    ticketStationDao.insert(Helpers.createTicketStationDB(args.ticketStation))
                    "Saved"
                }else{
                    ticketStationDao.update(Helpers.createTicketStationDB(args.ticketStation))
                    "Updeted"
                }
                Snackbar.make(
                    binding.root,
                    "$op ${args.ticketStation.properties.name}",
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }

    }


}