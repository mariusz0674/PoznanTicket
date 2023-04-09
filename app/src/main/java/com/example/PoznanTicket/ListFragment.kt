package com.example.PoznanTicket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.PoznanTicket.database.TicketStationDatabase
import com.example.PoznanTicket.ticketstations.TicketStation
import com.example.PoznanTicket.ticketstations.Helpers
//import com.example.PoznanTicket.database.BikeStationDatabase
//import com.example.PoznanTicket.databinding.FragmentListBinding
import com.example.PoznanTicket.network.BikeStationApi
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import poznanticket.databinding.FragmentListBinding
import java.lang.Exception

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListFragment : Fragment() {

    private var ticketStations: List<TicketStation>? = null
    private lateinit var binding: FragmentListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentListBinding.inflate(inflater, container, false)

        return binding.root

    }

    fun clearList() {
        val adapter = binding.bikeStationsList.adapter ?: return
        (adapter as BikeStationsListAdapter).clearList()
        binding.updateInfo.visibility = View.VISIBLE
        binding.progressBar.visibility = View.INVISIBLE
    }




    fun showList(){
        // SHows the list and sets the item click listener
        binding.updateInfo.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.INVISIBLE
        with(binding.bikeStationsList) {
            layoutManager = LinearLayoutManager(context)
            adapter = BikeStationsListAdapter(ArrayList(ticketStations), object :
                BikeStationListEventListener {
                override fun clickListener(
                    position: Int,
                    ticketStation: TicketStation
                ) {
                    val actionListFragmentToDetailFragment =
                        ListFragmentDirections.actionListFragmentToDetailFragment(
                            ticketStation
                        ) // safe args are used to pass data between fragemnts
                    findNavController().navigate(actionListFragmentToDetailFragment)
                }

            }
            )
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(ticketStations != null)
            showList()
    }

    fun populateListFromInternet(){
        binding.updateInfo.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE

        GlobalScope.launch{
            getBikeStationsFromRetrofit()

            withContext(Dispatchers.Main){
                Snackbar.make(
                    binding.root,
                    "${ticketStations?.size}" + " Ticket stations",
                    Snackbar.LENGTH_LONG
                ).show()
                showList()
            }
        }
    }
    fun populateListFromDB(){
        binding.updateInfo.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE
        GlobalScope.launch {
            getTicketStationsFromRoom()

            withContext(Dispatchers.Main){
                Snackbar.make(
                    binding.root,
                    "${ticketStations?.size} bike stations",
                    Snackbar.LENGTH_LONG
                ).show()

                if(ticketStations?.isNotEmpty() == true){
                    showList()
                }else{
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.updateInfo.visibility = View.VISIBLE
                }
            }

        }

    }

   private suspend fun getTicketStationsFromRoom() {
        val ticketStationDao =
            TicketStationDatabase.getInstance(requireContext()).ticketStationDatabaseDao

        val async = GlobalScope.async {
            withContext(Dispatchers.IO){
                    ticketStationDao.getAll()
                }
            }

        ticketStations = Helpers.createTicketStationList(async.await()).toList()
    }

    private suspend fun getBikeStationsFromRetrofit() {
        try{
            ticketStations=GlobalScope.async {
                withContext(Dispatchers.IO){
                BikeStationApi.retrofitService.getBikeStations().items
            }
        }.await()
    }catch(e: Exception){
        Log.e(activity?.localClassName, e.message.toString())
        }
    }

    private suspend fun getBikeStationsFromRetrofitWithQuery(){
        try{
            ticketStations = GlobalScope.async{
                withContext(Dispatchers.IO){
                    BikeStationApi.retrofitService.getBikeStations(
                        "pub_transport",
                        "stacje_rowerowe"
                    ).items
                }
            }.await()
        }catch(e: Exception){
            Log.e(activity?.localClassName, e.message.toString())
        }

    }



}
