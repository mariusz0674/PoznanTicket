package com.example.PoznanTicket

import android.animation.Animator
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.PoznanTicket.database.TicketStationDatabase
//import com.example.PoznanTicket.database.BikeStationDatabase
//import com.example.PoznanTicket.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import poznanticket.R
import poznanticket.databinding.ActivityMainBinding

private lateinit var appBarConfiguration: AppBarConfiguration
class MainActivity : AppCompatActivity(), Animator.AnimatorListener {
    // view binding variable that allows for easy access to the Activities' Views
    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    private var firstStart: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.addOnDestinationChangedListener(NavController.OnDestinationChangedListener { controller, destination, arguments ->
            if (!firstStart) {
                binding.fab.hide()
            }
            // Each time we change the navigation destination the menu will be invalidated
            invalidateOptionsMenu()
        })
        binding.fab.addOnHideAnimationListener(this)

        binding.fab.setOnClickListener {
            // placeholder for the FAB click event implementation
            val navHostFragment: NavHostFragment? =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment?
            if (navController.currentDestination?.id == R.id.listFragment) {
                val listFragment: ListFragment? =
                    navHostFragment?.childFragmentManager?.primaryNavigationFragment as ListFragment?
                listFragment?.clearList()
                listFragment?.populateListFromInternet()
            }
            else if (navController.currentDestination?.id == R.id.detailFragment) {
                val detailFragment: DetailFragment? =
                    navHostFragment?.childFragmentManager?.primaryNavigationFragment as DetailFragment?
                detailFragment?.saveToDatabase()
            }
        }
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val navDestination =
            navController.currentDestination ?: return super.onPrepareOptionsMenu(menu)
        return if (navDestination!!.id == R.id.detailFragment) {
            false // the menu disappears when the detailFragment is displayed
        } else {
            super.onPrepareOptionsMenu(menu)
        }

    }
    override fun onResume(){
        super.onResume()
        firstStart = false;
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (navController.currentDestination?.id == R.id.listFragment) {
            val navHostFragment: NavHostFragment? =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment?

            val listFragment: ListFragment? =
                navHostFragment?.childFragmentManager?.primaryNavigationFragment as ListFragment?

            listFragment?.clearList()

//            val queryId: BikeStationDatabase.QUERYID
//
//            // Perform actions for different menu options
//            when (item.itemId) {
////                R.id.get_all -> {
////                    queryId = BikeStationDatabase.QUERYID.GET_ALL
////                }
////                R.id.get_with_bikes -> {
////                    queryId = BikeStationDatabase.QUERYID.GET_STATIONS_WITH_BIKES
////
////                }
////                R.id.get_with_racks -> {
////                    queryId = BikeStationDatabase.QUERYID.GET_STATIONS_WITH_FREE_RACKS
////
////                }
//                R.id.clear -> {
//                    clearDB()
//                    return true
//                }
//                else -> return true
//            }
            if(item.itemId == R.id.clear){
                clearDB()
//                    return true
            }
            listFragment?.populateListFromDB()
        }
        return  super.onOptionsItemSelected(item)
    }

    private fun clearDB() {
        val ticketStationDao =
            TicketStationDatabase.getInstance(applicationContext).ticketStationDatabaseDao
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                ticketStationDao.clear()
            }
        }
    }

    override fun onAnimationStart(p0: Animator?) {
    }

    override fun onAnimationEnd(p0: Animator?) {
        val currentDestination =
            findNavController(R.id.nav_host_fragment_content_main).currentDestination
        if(currentDestination?.id == R.id.listFragment){
            binding.fab.setImageDrawable(getDrawable(R.drawable.ic_baseline_update_24))
        }else if(currentDestination?.id == R.id.detailFragment){
            binding.fab.setImageDrawable(getDrawable(R.drawable.ic_baseline_save_24))
        }
        binding.fab.show()
    }

    override fun onAnimationCancel(p0: Animator?) {
    }

    override fun onAnimationRepeat(p0: Animator?) {
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}