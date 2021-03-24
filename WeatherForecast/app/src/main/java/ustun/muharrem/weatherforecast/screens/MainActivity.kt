package ustun.muharrem.weatherforecast.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import ustun.muharrem.weatherforecast.R
import ustun.muharrem.weatherforecast.utilities.IS_CELSIUS_SETTING_KEY
import ustun.muharrem.weatherforecast.utilities.SharedPrefs

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initializeToolbar()
    }

    private fun initializeToolbar() {
        val host =
            supportFragmentManager.findFragmentById(R.id.fragment_container_main_activity) as NavHostFragment
        val navController = host.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}