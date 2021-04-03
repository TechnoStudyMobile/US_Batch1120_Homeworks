package ustun.muharrem.weatherforecast.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import ustun.muharrem.weatherforecast.R
import ustun.muharrem.weatherforecast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var forecastViewModel: ForecastViewModel
//    val requestPermissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { permissionGiven -> isGranted = permissionGiven }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initializeToolbar()
//        val factory = ForecastViewModelFactory(application)
//        forecastViewModel =
//            ViewModelProvider(this, factory).get(ForecastViewModel::class.java)
//        forecastViewModel.initializeAppLangCode()
//        forecastViewModel.initializeLocationService(applicationContext)

    }

//    override fun onResume() {
//        super.onResume()
//        forecastViewModel.subscribeToLocationUpdates()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        forecastViewModel.unsubscribeToLocationUpdates()
//    }

    private fun initializeToolbar() {
        val host =
            supportFragmentManager.findFragmentById(R.id.fragment_container_main_activity) as NavHostFragment
        val navController = host.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}